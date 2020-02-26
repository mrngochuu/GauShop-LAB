<%-- 
    Document   : newjsp
    Created on : Feb 26, 2020, 10:58:26 PM
    Author     : ngochuu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link rel="stylesheet" href="css/huudn.css">
        <script type="text/javascript" src="js/huudn.js"></script>
    </head>
    <body>
        <div id="confirm">
            <div class="message"></div>
            <button class="yes">Yes</button>
            <button class="no">No</button>
        </div>
        <button onclick = 'functionConfirm("Do you like Football?", function yes() {
                    alert("Yes");
                },
                        function no() {
                            alert("no");
                        });'>submit</button>
    </body>
</html>
