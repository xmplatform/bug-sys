<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@include file="/webpage/include/head.jsp" %>

<html>
<head>
<script type="text/javascript" src="${ctxStatic}/ckeditor/ckeditor.js"></script>
</head>
<body>

<%--<div class="form-group">--%>
	<%--<label class="col-sm-2 control-label">内容详情:</label>--%>
	<%--<div class="col-sm-10">--%>
		<%--<textarea id="description" htmlEscape="true" path="description" rows="4" maxlength="200" class=" form-control input-xxlarge"/>--%>
		<%--<sys:ckeditor replace="description" uploadPath="/bug/bug" />--%>
	<%--</div>--%>
<%--</div>--%>


<%--<div class="form-group">--%>
	<%--<label class="col-sm-2 control-label">图片:</label>--%>
	<%--<div class="col-sm-10">--%>
		<%--<input type="hidden" id="image" name="image" value="${image}" />--%>
		<%--<sys:ckfinder input="image" type="thumb" uploadPath="/bug/bug" selectMultiple="false"/>--%>
	<%--</div>--%>
<%--</div>--%>

<%--<div class="form-group">--%>
	<%--<label class="col-sm-2 control-label">附件:</label>--%>
	<%--<div class="col-sm-10">--%>
		<%--<input type="hidden" id="file" name="file" value="${file}" />--%>
		<%--<sys:ckfinder input="file" type="thumb" uploadPath="/bug/bug" selectMultiple="false"/>--%>
	<%--</div>--%>
<%--</div>--%>

内容:<textarea id="shaonian" name="shaonian"></textarea>

<script type="text/javascript">
	window.onload = function() {
		CKEDITOR.replace('shaonian');
	}
</script>
</body>

</html>