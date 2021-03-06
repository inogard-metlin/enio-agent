<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${status}</title>

    <!-- Bootstrap -->
    <link href="/webjars/gentelella/1.4.0/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="/webjars/gentelella/1.4.0/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="/webjars/gentelella/1.4.0/build/css/custom.min.css" rel="stylesheet">
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <!-- page content -->
        <div class="col-md-12">
          <div class="col-middle">
            <div class="text-center text-center">
              <h1 class="error-number">${status}</h1>
              <h2>${error}</h2>
            </div>
          </div>
        </div>
        <!-- /page content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="/webjars/gentelella/1.4.0/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="/webjars/gentelella/1.4.0/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="/webjars/gentelella/1.4.0/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="/webjars/gentelella/1.4.0/vendors/nprogress/nprogress.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="/webjars/gentelella/1.4.0/build/js/custom.min.js"></script>
  </body>
</html>