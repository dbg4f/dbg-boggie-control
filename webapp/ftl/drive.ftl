<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Drive commands and history</title>

    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
  <script src="../js/html5shiv.min.js"></script>
  <script src="../js/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<div class="container">

    <h2>Command</h2>
    <form role="form"  action="drive" method="post">
          <div class="form-group">
            <label for="comment">X,Y</label>
            <input type="text" class="form-control" placeholder="80 100" name="coordinates">
          </div>
        <button type="submit" class="btn btn-default">Send</button>
    </form>

    <br>

    <#list contexts as context>
        <p>${context}
    </#list>

    <table>
        <tr>
            <td>111</td>
        </tr>
        <tr>
            <td>222</td>
        </tr>
    </table>


</div>



<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="../js/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>
</body>
</html>
