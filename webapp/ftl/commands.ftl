<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap 101 Template</title>

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

      <h2>Commands</h2>
          <p>List of commands:</p>
          <form role="form"  action="sendCommands" method="post">
            <div class="form-group">
              <label for="comment">JSON:</label>
              <textarea class="form-control" rows="10" id="commands" name="commands"></textarea>
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
          </form>

             <br>
             <p>
                 <a href="../movement-view.html" class="btn btn-info btn-lg">Chart</a>
                 <a href="reset" class="btn btn-info btn-lg">Reset/Clear</a>
                 <a href="sendReportsOn" class="btn btn-info btn-lg">sendReportsOn</a>
                 <a href="sendReportsOff" class="btn btn-info btn-lg">sendReportsOff</a>
                 <a href="version" class="btn btn-info btn-lg">Version</a>
                 <a href="runMarkers" class="btn btn-info btn-lg">runMarkers</a>
                 <a href="runSine" class="btn btn-info btn-lg">runSine</a>
             </p>


     </div>



    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../js/bootstrap.min.js"></script>
  </body>
</html>
