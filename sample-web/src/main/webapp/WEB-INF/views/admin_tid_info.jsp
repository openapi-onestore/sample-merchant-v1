<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
  <head>
  <%@ page isELIgnored="false" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">
	
    <title>Open api admin</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value='/openapi/dist/css/bootstrap.min.css' />" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value='/openapi/css/starter-template.css' />" rel="stylesheet">
	
    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="<c:url value='/openapi/assets/js/ie-emulation-modes-warning.js' />"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
    
    <script type="text/javascript">   

	var dev_url = "http://172.21.60.143:8181";
	var sandbox_url = "http://172.21.142.194:8181";
    
	    (function($){
	    	count = 0;
	    	
	    	bulkJobRefundRequest = function() {
				$.ajax( {
					url: dev_url + "/sample-web/openapi/payment/refund/${tid}"
				}).success( function( data ) {
					console.log( data );
					
					$( ".panel-body" ).html( data );
				
				}).error( function( data ) {
					console.log( "Ajax Error" );				
				});
	    	}					

	    })(jQuery, undefined);


    </script>    
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Payplanet Open API - Merchant</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>           
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	
<!-- 
    <div class="container">
      <div class="starter-template">
        <h1>Bootstrap starter template</h1>
        <p class="lead">Use this document as a way to quickly start any new project.<br> All you get is this text and a mostly barebones HTML document.</p>
      </div>
    </div>
-->

    <div class="container theme-showcase" role="main" align="left">
	
      <div class="page-header" align="center">
        <h1>Open API test page - transaction id information</h1>
      </div>
<!--   
      <p align="center">
        <button type="button" class="btn btn-lg btn-default">Default</button>
        <button type="button" class="btn btn-lg btn-primary">Primary</button>
        <button type="button" class="btn btn-lg btn-success">Success</button>
        <button type="button" class="btn btn-lg btn-info">Info</button>
        <button type="button" class="btn btn-lg btn-warning">Warning</button>
        <button type="button" class="btn btn-lg btn-danger">Danger</button>
        <button type="button" class="btn btn-lg btn-link">Link</button>
      </p>
-->
	
    <br/>
    	<!-- Result code -->
		<div class="input-group" style="width: 250px;">
		  <span class="input-group-addon" id="sizing-addon2" style="width:100px">resultCode</span>
		  <input type="text" class="form-control" placeholder="None" aria-describedby="sizing-addon2" value='${resultCode}'>
		</div>
		<div class="input-group" style="width: 700px">
		  <span class="input-group-addon" id="sizing-addon2" style="width:100px">resultMsg</span>
		  <input type="text" class="form-control" placeholder="None" aria-describedby="sizing-addon2" value='${resultMsg}'>
		</div>
		
		<!-- Payer -->
		<div class="input-group" style="width: 400px; left: 100px;">
			<input type="text" class="form-control" aria-label="Amount (to the nearest dollar)" value="Payer" style="width: 150px">		  
			<span class="input-group-addon" id="sizing-addon2" style="width: 100px">authkey</span>
			<input type="text" class="form-control" aria-label="Amount (to the nearest dollar)" value='${authkey}'>
		</div>
				
		<!-- trInfo content -->
		<div class="input-group" style="width: 400px; left: 100px;">
			<input type="text" class="form-control" aria-label="Amount (to the nearest dollar)" value="TransactionInfo" style="width: 150px">		  
			<span class="input-group-addon" id="sizing-addon2" style="width: 100px">status</span>
			<input type="text" class="form-control" aria-label="Amount (to the nearest dollar)" value='${status}'>		  		  
		</div>
		<div class="input-group" style="width: 250px; left: 250px">
		  <span class="input-group-addon" id="sizing-addon2" style="width:100px">reason</span>
		  <input type="text" class="form-control" placeholder="None" aria-describedby="sizing-addon2" value='${reason}'>
		</div>
		<div class="input-group" style="width: 450px; left: 250px">
		  <span class="input-group-addon" id="sizing-addon2" style="width:100px">message</span>
		  <input type="text" class="form-control" placeholder="None" aria-describedby="sizing-addon2" value='${message}'>
		</div>
		<div class="input-group" style="width: 250px; left: 250px">
		  <span class="input-group-addon" id="sizing-addon2" style="width:100px">lastUpdate</span>
		  <input type="text" class="form-control" placeholder="None" aria-describedby="sizing-addon2" value='${latestUpdate}'>
		</div>
		<div class="input-group" style="width: 450px; left: 250px">
		  <span class="input-group-addon" id="sizing-addon2" style="width:100px">tid</span>
		  <input type="text" class="form-control" placeholder="None" aria-describedby="sizing-addon2" value='${tid}'>
		</div>
		<div class="input-group" style="width: 250px; left: 250px">
		  <span class="input-group-addon" id="sizing-addon2" style="width:100px">amount</span>
		  <input type="text" class="form-control" placeholder="None" aria-describedby="sizing-addon2" value='${amount}'>
		</div>
		<div class="input-group" style="width: 450px; left: 250px">
		  <span class="input-group-addon" id="sizing-addon2" style="width:100px">description</span>
		  <input type="text" class="form-control" placeholder="None" aria-describedby="sizing-addon2" value='${description}'>
		</div>
		
		<!-- goods -->
		<div class="input-group" style="width: 400px; left: 250px;">
			<input type="text" class="form-control" value="Goods" style="width: 100px">		  
			<span class="input-group-addon" id="sizing-addon2" style="width: 100px">appId</span>
			<input type="text" class="form-control" value='${goods[0].appId}'>		  		  
		</div>
		<div class="input-group" style="width: 300px; left: 350px;">
			<span class="input-group-addon" id="sizing-addon2" style="width: 100px">product id</span>
			<input type="text" class="form-control" value='${goods[0].productId}'>
		</div>
		
		<!-- payment method -->
		<div class="input-group" style="width: 400px; left: 250px;">
			<input type="text" class="form-control" value="Methods" style="width: 100px">		  
			<span class="input-group-addon" id="sizing-addon2" style="width: 100px">PayMethod</span>
			<input type="text" class="form-control" value='${paymentMethods[0].paymentMethod}'>		  		  
		</div>
		<div class="input-group" style="width: 300px; left: 350px;">
			<span class="input-group-addon" id="sizing-addon2" style="width: 100px">amount</span>
			<input type="text" class="form-control" value='${paymentMethods[0].amount}'>
		</div>
		
		<br/>
		
		<button type="button" class="btn btn-lg btn-default" onclick="bulkJobRefundRequest()">Refund request</button>
		
		<br/><br/>
		
		<div class="panel panel-default">
		  <div class="panel-heading">
		    <h3 class="panel-title">Refund request result from Onestore open api server</h3>
		  </div>
		  <div class="panel-body">
		    ...
		  </div>
		</div>
		
	</div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="<c:url value='/openapi/dist/js/bootstrap.min.js'/>"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="<c:url value='/openapi/assets/js/ie10-viewport-bug-workaround.js'/>"></script>
  </body>
</html>
