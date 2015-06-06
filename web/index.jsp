<html>
    <body>
        <h1>JAX-RS File Upload Example</h1>
	<form action="http://192.168.1.147:8080/sportsbook-core-web/register/upload-file" method="post" enctype="multipart/form-data">
            <p>
                File name : <input type="text" name="fileName" />
            </p>
            <p>
	        Choose the file : <input type="file" name="selectedFile" />
	    </p>
	                <p>
                Transaction Amount : <input type="text" name="transactionAmount" />
            </p>
            	                <p>
                Transaction Category : <input type="text" name="transactionCategory" />
            </p>
                        	                <p>
                Transaction Description : <input type="text" name="transactionDescription" />
            </p>
	    <input type="submit" value="Upload" />
	</form>
	http://www.howtodoinjava.com
    </body>
</html>
