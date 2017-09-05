package org.springrain.system.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

public class PropertyFileCache {
	private static final String ENCODING = "UTF-8";
	private static final String PARAMETER_DELIMITER = "=";
	private static final String COMMENT_DELIMITER = "#";
	private static final int PRINT_ELEMENT_LIMIT = 50;

	protected static PropertyFileCache instance;
	protected Hashtable<String, FileInfo> fileInfoCachedMap;

	private static final Logger logger = LoggerFactory.getLogger(PropertyFileCache.class);

	protected PropertyFileCache() {
		fileInfoCachedMap = new Hashtable<String, FileInfo>();
	}

	// get this instance - a singleton
	public static synchronized PropertyFileCache getInstance() {
		if (instance == null) {
			instance = new PropertyFileCache();
		}
		return instance;
	}

	// return an enumeration of all keys
	public Enumeration<String> getAllKeys(String fileName) {
		FileInfo curFileInfo = this.getFileInfo(fileName);
		Enumeration<String> allkeys = null;
		if (curFileInfo != null) {
			this.checkConf(curFileInfo);
			allkeys = curFileInfo.cachedTokens.keys();
		}
		return allkeys;
	}

	// return a set for all keys available
	public Set<String> keySet(String fileName) {
		Set<String> keyset = new HashSet<String>();
		FileInfo curFileInfo = this.getFileInfo(fileName);
		if (curFileInfo != null) {
			this.checkConf(curFileInfo);
			keyset = curFileInfo.cachedTokens.keySet();
		}
		return keyset;
	}

	// get the value based on the key and filename
	public String get(String fileName, String key) {
		String retStr = null;
		FileInfo curFileInfo = this.getFileInfo(fileName);
		if (curFileInfo != null) {
			this.checkConf(curFileInfo);
			retStr = curFileInfo.cachedTokens.get(key);
		}
		return retStr;
	}

	// check if the item contains the token
	public boolean contains(String fileName, String token) {
		boolean status = false;
		FileInfo curFileInfo = this.getFileInfo(fileName);
		if (curFileInfo != null) {
			this.checkConf(curFileInfo);
			status = curFileInfo.cachedTokens.contains(token);
		}
		return status;
	}

	// check if key is there!
	public boolean containsKey(String fileName, String token) {
		boolean status = false;
		FileInfo curFileInfo = this.getFileInfo(fileName);
		if (curFileInfo != null) {
			this.checkConf(curFileInfo);
			status = curFileInfo.cachedTokens.containsKey(token);
		}
		return status;
	}

	// return the number of items in the file
	public int getSize(String fileName) {
		int size = 0;
		FileInfo curFileInfo = this.getFileInfo(fileName);
		if (curFileInfo != null) {
			this.checkConf(curFileInfo);
			size = curFileInfo.cachedTokens.size();
		}
		return size;
	}

	// get the who hash table for internal processing
	public Hashtable<String, String> getProperty(String fileName) {
		FileInfo curFileInfo = this.getFileInfo(fileName);
		Hashtable<String, String> retTable = null;
		if (curFileInfo != null) {
			this.checkConf(curFileInfo);
			retTable = curFileInfo.cachedTokens;
		} else {
			retTable = new Hashtable<String, String>();
		}
		return retTable;
	}

	// return the timestamp of this file. this is used by external routine to explicit triggers a re-read
	public long getTimestamp(String fileName) {
		long ts = 0;
		FileInfo curFileInfo = this.getFileInfo(fileName);
		if (curFileInfo != null) {
			this.checkConf(curFileInfo);
			ts = curFileInfo.timestamp;
		}
		return ts;
	}

	// check to see if time stamp has been updated. This check DOES NOT trigger a re-read!
	public boolean isUpdated(String fileName) {
		boolean status = false;
		if (fileName != null && fileName.length() > 0) {
			FileInfo curFileInfo = this.getFileInfo(fileName);
			if (curFileInfo != null) {
				long newTimestamp = (new File(curFileInfo.filename)).lastModified();
				// if file does not exist, lastModified() will return '0', let it proceed to create a zero element HashTable
				if (newTimestamp > curFileInfo.timestamp || newTimestamp == 0) {
					// file has been updated!
					status = true;
				}
			} else {
				// signal update is required because file has not been read before!
				status = true;
			}
		} // else - if file name is invalid, there is nothing to be 'updated', return false...
		return status;
	}

	private FileInfo getFileInfo(String fileName) {
		FileInfo curFileInfo = null;
		if (fileName != null && fileName.length() > 0) {
			try {
				if (!fileInfoCachedMap.containsKey(fileName)) {
					fileInfoCachedMap.put(fileName, new FileInfo(this.getAbsolutePath(fileName)));
				}

				curFileInfo = fileInfoCachedMap.get(fileName);
			} catch (Exception e) {
				curFileInfo = null;
				logger.error("Cannot read simple property file [{}]", fileName);
			}
		}
		return curFileInfo;
	}

	private void checkConf(FileInfo fileInfo) {
		try {
			// if 'fi' is null, meaning cannot read a file, will throw exception
			long newTimestamp = (new File(fileInfo.filename)).lastModified();

			// if file does not exist, lastModified() will return '0', let it proceed to create a zero element HashTable
			if (newTimestamp > fileInfo.timestamp || newTimestamp == 0) {
				// similar to Properties.load(), but have to deal with UTF-8!
				this.parseProperties(fileInfo);

				fileInfo.timestamp = newTimestamp;
				logger.info("Parsed file [{}] with timestamp =[{}]", fileInfo.filename, fileInfo.timestamp);
			}
		} catch (Exception e) {
			logger.error("Cannot read file [{}] from directory...{}", fileInfo.filename, e.getMessage());
		}
	}

	private void parseProperties(FileInfo fileInfo) {
		if (fileInfo.filename == null || fileInfo.filename.length() == 0) {
			logger.error("Property file [{}] is not defined", fileInfo.filename);
			return;
		}
		Hashtable<String, String> tokensFromFile = new Hashtable<String, String>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileInfo.filename), ENCODING));

			String tmpValue = null;
			String tmpKey = null;
			String tmpLine = null;
			while ((tmpLine = br.readLine()) != null) {
				// strip off any comment and trim it down
				tmpLine = tmpLine.replaceAll(COMMENT_DELIMITER + ".*$", "").trim();
				int sindex = tmpLine.indexOf(PARAMETER_DELIMITER);

				if (sindex != -1) {
					tmpKey = tmpLine.substring(0, sindex).trim();
					tmpValue = tmpLine.substring(sindex + 1).trim();

					if ( /* tmpValue.length() > 0 && */tmpKey.length() > 0) {
						// allow empty string to be a variable...
						tokensFromFile.put(tmpKey, tmpValue);
					}
				}
			}
			logger.info("Property file [{}] loaded with {} element(s).", fileInfo.filename, tokensFromFile.size());
		} catch (Exception e) {
			logger.error("Property file [{}] cannot be loaded {}", fileInfo.filename, e.getMessage());
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
			}

			if (tokensFromFile != null) {
				if (fileInfo.cachedTokens != null) {
					// remove old Vector
					fileInfo.cachedTokens.clear();
				}
				// use new table
				fileInfo.cachedTokens = tokensFromFile;
				if (fileInfo.cachedTokens.size() < PRINT_ELEMENT_LIMIT) {
					// if there are too many, do not print...
					logger.debug("Property file containing elements...\n{}", fileInfo.cachedTokens.toString());
				}
			}
		}

	}
	
	// get the absolute path
	private String getAbsolutePath(String resourceLocation) {
		String path = resourceLocation.replace(ResourceUtils.CLASSPATH_URL_PREFIX, StringUtils.EMPTY);
		URL url = ClassUtils.getDefaultClassLoader().getResource(path);
		if (url == null) {
			logger.error("can not get the absolute path of file [{}]", resourceLocation);
			return resourceLocation;
		}
		try {
			return URLDecoder.decode(url.getPath(), CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	final class FileInfo {
		private String filename = null;
		private long timestamp = 0;
		private Hashtable<String, String> cachedTokens;

		private FileInfo(String fileName) {
			this.filename = fileName;
			// no need to initialize timestamp and cachedTokens, it will be determined later
		}

	}

}

// 调用示例
// String value = PropertyFileCache.getInstance().get("./bin/servletmap/instr2url.properties", "key");
