<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
  <head>
    <meta charset="utf-8">
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
	    	
	    	bulkJobRequest = function() {
				$.ajax( {
					url: dev_url + "/sample-web/openapi/payment/request"
				}).success( function( data ) {
					console.log( data );
					
					if (count != data.length) {
						for( var i = 0; i < data.length; i++ ) {
							var $rowHtml = $( "<tr>" +
		    						"<td class='mid'></td>"+
		    						"<td class='statusBtn'><button type = 'button' class='btn btn-sm'></button></td>"+
		    						"<td class='resultCode'></td>" +
		    						"<td class='resultMsg'></td>" +
		    						"<td class='waitingJob'></td>" +
		    						"<td><a class='jobId'><button type = 'button' class='btn btn-sm btn-link'/></a></td>" + 
		    						"<td class='uploadFile'></td>" +
		    						"<td class='uploadDate'></td>" +
		    						"<td class='verifyMessage'></td>" +
								"</tr>" );
							console.log( data[i].resultCode);
							$rowHtml.find( ".mid" ).html( data[ i ].mid );
							$rowHtml.find( ".statusBtn>button" ).html( data[ i ].status ).addClass( ( data[ i ].status === "SUCCESS" ) ? "btn-success" : "btn-danger" );							
							$rowHtml.find( ".resultCode" ).html( data[ i ].resultCode );
							$rowHtml.find( ".resultMsg" ).html( data[ i ].resultMsg );							
							$rowHtml.find( ".waitingJob" ).html( data[ i ].waitingJob );
							$rowHtml.find( ".jobId" ).html(data[ i ].jobId );
							$rowHtml.find( ".jobId" ).attr( "href", dev_url + "/sample-web/openapi/admin/result/" + data[ i ].jobId );
							$rowHtml.find( ".uploadFile" ).html( data[ i ].uploadFile );
							$rowHtml.find( ".uploadDate" ).html( data[ i ].uploadDate );
							$rowHtml.find( ".verifyMessage" ).html( data[ i ].verifyMessage );
							
							$( "table.table>tbody" ).append( $rowHtml );	
						}
					}
					
					count = data.length;
				}).error( function( data ) {
					console.log( "Ajax Error" );				
				});
	    	}					

	    })(jQuery, undefined);


    </script>
    
  </head>

  <body onload="bulkJobRequest()">

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

    <div class="container theme-showcase" role="main">
	    
      <div class="page-header" align="center">
        <h1>Open API test page</h1>
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

	<h4>File upload status : <%=request.getAttribute("uploadResult") %></h4><br/>

    <form action="<c:url value='/openapi/payment/bulkjob'/>" method="post" enctype="multipart/form-data">
		<input type="file" name="bulkjob" />
		<br/>
		<input type="submit" value="upload" />
	</form>
    <br/>
    <button type="button" class="btn btn-sm btn-primary" onclick='bulkJobRequest()'>BulkJob Request update</button>
    <br/>
    <br/>
      <div class="page-content" align="center">
         <table class="table table-striped">
            <thead>
              <tr>
                <th>#</th>
                <th>Req Status</th>
                <th>Result Code</th>
                <th>Result Msg</th>                
                <th>Waiting jobs</th>
                <th>Job id</th>
                <th>Upload file</th>
                <th>Upload date</th>
                <th>Received noti</th>
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
