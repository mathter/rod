<%@ page isErrorPage="true" import="java.io.*" contentType="text/html"%>
<!DOCTYPE html>
<html>
<head>
<title>Error page</title>
</head>
<body>
	<table style="width: 100%; border: solid black 1px;">
		<tr>
			<th>Message:</th>
		</tr>
		<tr>
			<td><%=exception != null ? exception.getMessage() : null%></td>
		</tr>
	</table>
	<br />
	<table style="width: 100%; border: solid black 1px;">
		<tr>
			<th>StackTrace:</th>
		</tr>
		<tr>
			<td>
				<%
				    if ( exception != null )
				    {
				        StringWriter stringWriter = new StringWriter();
				        PrintWriter printWriter = new PrintWriter( stringWriter );

				        exception.printStackTrace( printWriter );
				        out.println( stringWriter );
				        printWriter.close();
				        stringWriter.close();
				    }
				%>
			</td>
		</tr>
	</table>
</body>
</html>