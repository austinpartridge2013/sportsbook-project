<html>
    <body>
        <h1>Easybooks!</h1>
	<form action="http://192.168.1.147:8080/easybooks-core-web/register/upload-file" method="post" enctype="multipart/form-data">
            <p>
                File name : <input id="fileName" type="text" name="fileName" />
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
                        	                <p>
                Transaction Year : <input type="text" name="transactionYear" />
            </p>
                                    	                <p>
                Transaction Month : <input type="text" name="transactionMonth" />
            </p>
                                    	                <p>
                Transaction Day : <input type="text" name="transactionDay" />
            </p>
	    <input type="submit" value="Upload" />
	</form>
    </body>
</html>
