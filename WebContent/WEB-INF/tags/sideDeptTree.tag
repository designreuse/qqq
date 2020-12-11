<%@tag description="左树右grid画面布局中，左侧的公司部门树" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="treeUrl" required="true" description="树请求ajax对应的URL"%>
<%@attribute name="linkedGridId" required="true" description="关联的外部datagrid的id，加载或点击树的节点时，将刷新外部的datagrid"%>
<%@attribute name="linkedSubgridId" description="关联的外部datagrid的id，加载或点击树的节点时，将清空外部的datagrid"%>

<div class="easyui-layout" data-options="fit:true">

  <div data-options="region:'center',border:false" style="padding: 6px 5px 5px 5px;">
    <div class="easyui-layout" data-options="fit:true">

      <%-- 刷新按钮 --%>
      <div data-options="region:'north',border:false" style="padding-bottom: 10px">
        <div class="container-fluid min-width-200">
          <div class="row row-gapped-top-s">
            <div class="col-xs-12 col-gapped-xs">
              <a href="javascript:void(0)" class="easyui-linkbutton c8" data-options="width:'100%'" role="button" onclick="$('#${idPrefix}-SideTree').tree('reload');">
                <s:message code="ui.common.refresh" />
              </a>
            </div>
          </div>
        </div>
      </div>
      <!-- center.north region -->

      <%-- 部门树 --%>
      <div data-options="region:'center',border:true">
        <ul id="${idPrefix}-SideTree" class="easyui-tree" data-linked-grid-id="${linkedGridId}" <c:if test="${not empty linkedSubgridId}">data-linked-subgrid-id="${linkedSubgridId}"</c:if>
          data-options="url:'${treeUrl}',onSelect:SideUtils.handleTreeNodeSelect,
            loadFilter:MainUtils.treeLoadFilter,onLoadSuccess:SideUtils.handleTreeLoadSuccess,onLoadError:MainUtils.handleDatagridLoadError"
        ></ul>
      </div>
      <!-- center.center region -->

    </div>
    <!-- /.easyui-layout in center region -->
  </div>
  <!-- center region -->

  <div data-options="region:'east',border:false,width:2,minWidth:2,maxWidth:2">
    <hr class="vbar" />
  </div>
  <!-- east region -->

</div>
<!-- /.easyui-layout -->