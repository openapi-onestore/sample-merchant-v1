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
	    	
	    	bulkJobResultRequest = function() {
				$.ajax( {
					url: dev_url + "/sample-web/openapi/payment/result/${jobid}"
				}).success( function( data ) {
					console.log( data );
					if (data.length <= 20) {
						console.log( 'data length is not vaild' );
					} else {
						var original = data.split('\n');
						console.log( original );
						console.log( original[0]);
						
						for( var i = 2; i < original.length-1; i++) {
							data = original[i].split(',');
							console.log( data );
							
							var $rowHtml = $( "<tr>" +
		    						"<td class='id'></td>"+		    						
		    						"<td class='cmId'></td>" +
		    						"<td class='bid'></td>" +
		    						"<td class='billingToken'></td>" + 
		    						"<td class='productId'></td>" +
		    						"<td class='nmProduct'></td>" +
		    						"<td class='noBranchOrder'></td>" +
		    						"<td class='amtReqPurchase'></td>" +
		    						"<td class='amtCarrier'></td>" +
		    						"<td class='amtCreditCard'></td>" +
		    						"<td class='amtTms'></td>" +
		    						"<td class='tid'></td>" +
		    						"<td class='cdResult'></td>" +
		    						"<td class='msgResult'></td>" +
								"</tr>" );
							$rowHtml.find( ".id" ).html( data[0] );
							$rowHtml.find( ".cmId" ).html( data[1] );
							$rowHtml.find( ".bid" ).html( data[2] );
							$rowHtml.find( ".billingToken" ).html( data[3].substr(0,8) + '..' );
							$rowHtml.find( ".productId" ).html( data[4] );							
							$rowHtml.find( ".nmProduct" ).html( data[5] );
							$rowHtml.find( ".noBranchOrder" ).html( data[6] );							
							$rowHtml.find( ".amtReqPurchase" ).html( data[7] );
							$rowHtml.find( ".amtCarrier" ).html( data[8] );
							$rowHtml.find( ".amtCreditCard" ).html( data[9] );
							$rowHtml.find( ".amtTms" ).html( data[10] );
							$rowHtml.find( ".tid" ).html( ( data[11] != "None" ) ? "<a href=http://172.21.60.143:8181/sample-web/openapi/admin/transaction/"+data[11]+">"+data[11]+"</a>" : "None");
							$rowHtml.find( ".cdResult" ).html( data[12] );
							$rowHtml.find( ".msgResult" ).html( data[13] );
							
							$( "table.table>tbody" ).append( $rowHtml );
						}						
					}
										
				}).error( function( data ) {
					console.log( "Ajax Error" );				
				});
	    	}					

	    })(jQuery, undefined);


    </script>
    
  </head>

  <body onload="bulkJobResultRequest()">

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

    <div class="container theme-showcase" role="main" style="width: 1800px;">
	
      <div class="page-header" align="center">
        <h1>Open API test page - payment result</h1>
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
      <div class="page-content" align="center" style="width: 1800px;">
         <table class="table table-striped">
            <thead>
              <tr>
                <th>id</th>
                <th>cmId</th>
                <th>bid</th>
                <th>bToken</th>
                <th>Pid</th>
                <th>Pname</th>
                <th>noBranchOrder</th>                
                <th>ReqPurchase</th>
                <th>Carrier</th>
                <th>CreditCard</th>
                <th>Tms</th>
                <th>tid</th>
                <th>cdResult</th>
                <th>msgResult</th>
              </tr>
            </thead>
            <tbody>

            </tbody>
          </table>
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
