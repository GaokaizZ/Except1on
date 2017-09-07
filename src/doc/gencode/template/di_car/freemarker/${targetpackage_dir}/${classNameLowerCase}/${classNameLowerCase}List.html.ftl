${r"<#escape x as x?html>"}
<#assign className = table.className>   
<#assign tableName = table.tableAlias>   
<#assign classNameLower = className?uncap_first>  
<#assign classNameLowerCase = className?lower_case>
<#assign from = basepackage?last_index_of(".")>
<#assign rootPagefloder = basepackage?substring(basepackage?last_index_of(".")+1)>

<script type="text/javascript" src="${r"${ctx}"}/js/${rootPagefloder}/${classNameLowerCase}/${classNameLowerCase}.js"></script>
<#list table.columns as column>
<#if column.isDateTimeColumn>	
<script type="text/javascript" src="${r"${ctx}"}/js/my97/WdatePicker.js"></script>
<#break>
</#if>
</#list>

<div class="operate panel panel-default" style="height:65px;">
	<div class="panel-body">
		<div class="pull-left">
			<form class="form-horizontal" id="searchForm" name="searchForm" action="${r"${ctx}"}/${classNameLowerCase}/list" method="post">
				<input type="hidden" name="pageIndex" id="pageIndex" value="${r"${(returnDatas.page.pageIndex)!'1'}"}" />
				<input type="hidden" name="sort" id="page_sort" value="${r"${(returnDatas.page.sort)!'desc'}"}" />
				<input type="hidden" name="order" id="page_order" value="${r"${(returnDatas.page.order)!'id'}"}" />
				
				<#list table.columns as column>
				<#if !column.pk>
				
				<#if column.isDateTimeColumn>
				<#assign columnDataValue = "(("+classNameLower+"."+column.columnNameFirstLower+")?string('yyyy-MM-dd'))!'' ">
				<label for="${column.columnNameFirstLower}"><b>${column.columnAlias}:</b></label>
				<input type="text" id="${column.columnNameFirstLower}_" name="${column.columnNameFirstLower}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" value="${r"${"}${columnDataValue}${r"}"}" class="inp_2"/>
				
				<#else>
				<label for="${column.columnNameFirstLower}"><b>${column.columnAlias}:</b></label>
				<input type="text" id="${column.columnNameFirstLower}_" name="${column.columnNameFirstLower}" value="${r"${"}(${classNameLower}.${column.columnNameFirstLower})!''${r"}"}" class="inp_2"/>
		        </#if>
		        
				</#if>
			   	</#list>
			   	
			   	<a href="javascript:mySubmitForm('searchForm');" class="btn btn-purple btn-sm">
					查询 <i class="ace-icon fa fa-search icon-on-right bigger-10"></i>
				</a>
			</form>
		</div>

		<div class="pull-right">
			${r"<@shiro.hasPermission"} name="/${classNameLowerCase}/update" >
			<button onclick="commonSaveForm('updateForm','${r"${ctx}"}/${classNameLowerCase}/list');" class="btn  btn-sm  btn-primary">
				添加
			</button>
			<button onclick="commonUpdateForm('updateForm','${r"${ctx}"}/${classNameLowerCase}/list');" class="btn  btn-sm  btn-primary">
				修改
			</button>
			${r"</@shiro.hasPermission>"}
			
			${r"<@shiro.hasPermission"} name="/${classNameLowerCase}/delete" >
			<button onclick="delete${className}();" class="btn btn-sm  btn-danger">
				 删除
			</button>
			${r"</@shiro.hasPermission>"}
		</div>
	</div>
</div>

<div class="row">
	<div class="col-sm-4 sub_left_menu ">
		<table class="table table-bordered">
			<thead>
				<tr>
				<#list table.columns as column>
				<#if !column.pk>
					<th>${column.columnAlias}</th>
				</#if>
				</#list>
				</tr>
			</thead>
			<tbody>
				
			${r"<#if (returnDatas.data??)&&(returnDatas.data?size>0)>"}
			
				${r"<#list returnDatas.data as _data>"}
						<tr id="${r"${(_data.id)!''}"}">
						
							<#list table.columns as column>
							<#if !column.pk>
							<td>
								<#if column.isDateTimeColumn>
									<!--日期型-->
									<#assign columnDataValue = "((_data." + column.columnNameFirstLower+")?string('yyyy-MM-dd'))!''"> 
									${r"${"}${columnDataValue}${r"}"}
									
								<#elseif column.javaType == 'java.lang.Boolean'>
									<!--布尔型-->
									<#assign columnBooleanValue = "(_data." + column.columnNameFirstLower+")">
									${r'<#if'} ${columnBooleanValue}?? && ${columnBooleanValue} >
									是
									${r'<#else>'}
									否
									${r'</#if>'}
									
								<#elseif column.isNumberColumn>
									<!--数字型-->
									${r"${(_data."}${column.columnNameFirstLower}${r")!0}"}
									
								<#else>
									${r"${(_data."}${column.columnNameFirstLower}${r")!''}"}
								</#if>
							</td>
							</#if>
							</#list>
						
						</tr>
				${r"</#list>"}
			${r"</#if>"}
			</tbody>
		</table>
	</div>
	
	<div class="col-sm-8 last sub_content">
		<form class="well form-horizontal clearfix" id="updateForm" name="updateForm" action="${r"${ctx}"}/${classNameLowerCase}/update" method="post">
			<!--input hidden Start-->
			<#list table.columns as column>
				<#if column.pk>					
				<input type="hidden" id="${column.columnNameFirstLower}" name="${column.columnNameFirstLower}" />	
				</#if>
			</#list>
			<!--input hidden End-->
			
			<#list table.columns as column>
				<#if !column.pk>
				<div class="form-group col-xs-12">
					<label class="col-sm-4 control-label" for="${column.columnNameFirstLower}"><#if !column.nullable><span >*</span></#if>${column.columnAlias}：</label>
					<#if column.isDateTimeColumn>
						<!--日期型-->
						<#assign columnDataValue = "(("+classNameLower+"."+column.columnNameFirstLower+")?string('yyyy-MM-dd'))!'' ">
						<div class="col-sm-8">
							<input class="form-control" type="text" id="${column.columnNameFirstLower}" name="${column.columnNameFirstLower}" 
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" />
						</div>
						
					<#else>
						<div class="col-sm-8">
							<input class="form-control" type="text" id="${column.columnNameFirstLower}" name="${column.columnNameFirstLower}" placeholder="请输入${column.columnAlias}" />
						</div>
					</#if>
				</div>
				</#if>
			</#list>				
			
		</form>
	</div>
	
		${r"<#if returnDatas.page??>"}
			${r"<@h.pagetoolbar page=returnDatas.page formId='searchForm' />"}
		${r"</#if>"}
</div>

${r"</#escape>"}


