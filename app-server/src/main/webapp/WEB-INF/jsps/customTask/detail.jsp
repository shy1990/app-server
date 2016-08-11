<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>首页</title>
<link rel="stylesheet" href="/static/css/bootstrap.min.css"
	media="screen" />
<link rel="stylesheet" href="/static/css/index.css" type="text/css">

</head>
<body>

	<div class="row ">
		<div class="container">
			<div class="col-xs-1"></div>

			<div class="col-xs-10">
				<div class="H-main">
					<div class="head-main">
						<span class="text-heda">${task.title}</span>
						<h5>
							类型：${taskType} &nbsp; &nbsp;&nbsp;<span class="text-red"
								id="punish">扣罚：${task.punishCount}元</span> <span
								class="text-right">${taskTime}</span>
						</h5>
					</div>
					<hr class="htrr">
					<p class="text-body">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${task.content}</p>
					<br>

				</div>
				<div class="text-page">
					全部 <span class="red-two">${messagesCount}</span> 条
				</div>
			</div>
			<div class="col-xs-1"></div>
		</div>


		<div class="container H-main-d">
			<div class="col-xs-1"></div>
			<div class="col-xs-10">
				<c:if test="${not empty messageList}">
					<c:forEach var="message" items="${messageList}">
						<c:if test="${message.roletype==0}">
							<div class="H-main">
								<div class="box-new">
									<img src="/static/image/yello.png" style="float: left">
									<p class="biat">
										<span class="text-blue">信息发布者</span> <span class="text-gery">
											${message.ctime}</span>
									</p>
									<br>
									<p class="p-style">${ message.content}</p>
								</div>
							</div>

							<hr class="htrrr">
						</c:if>
						<c:if test="${message.roletype==1}">
							<div class="H-main">
								<div class="box-new">
									<img src="/static/image/blue.png" style="float: right">
									<p class="biat-r">
										<span class="text-blue">我</span> <span class="text-gery">
											${message.ctime}</span>
									</p>
									<br>
									<p class="p-style-r">${message.content}</p>
								</div>
							</div>

							<hr class="htrrr">
						</c:if>
					</c:forEach>
				</c:if>

			</div>
			<div class="col-xs-1"></div>
		</div>


		<div class="send-new-user " id="J_sendNewUser" data-spm="918"
			style="display: block; left: 0px;">
			<div class="tishi">提示：本条信息需要回执单</div>
			<button class="btn btn-orage in" id="huizhi">回 执</button>
		</div>


		<div class="send-new " id="J_sendNew" data-spm="918">

			<div class="col-xs-1"></div>

			<div class="col-xs-10">
				<p class="text-white">回执</p>
				<textarea id="message" class="form-control" rows="5"
					placeholder="请输入回执内容"></textarea>
				<p class="text-white-w">
					可以输入 <span class="text-orage">2000</span> 个字符
				</p>
				<button id="repButton" class="btn btn-fab fr" onclick="sendMessage();">发布回执</button>

			</div>
			<div class="col-xs-1"></div>

		</div>

	</div>

	<script type="text/javascript"
		src="/static/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/static/customTask/detail.js"></script>
	<script type="text/javascript">
		var btn1 = $('#huizhi');
		var box1 = $('#J_sendNewUser');
		$("#huizhi.in").on("click", function() {
			$("#J_sendNew").removeClass('active');
		})
		$("#huizhi").on("click", function() {
			$("#J_sendNew").addClass('active');
			$(this).addClass('in');
		})
		var taskId = "${taskId}";
		var salesmanId = "${salesmanId}";
		var type = "${task.type}";
		if (type != 2) {
			$("#punish").css("display", "none");
		}
	</script>
</body>
</html>