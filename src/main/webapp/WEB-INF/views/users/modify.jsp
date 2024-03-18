<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Utilisateurs
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post" action="/rentmanager/users/modify_?id=${client.idClient}">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="last_name" class="col-sm-2 control-label">Nom</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="last_name" name="last_name" value="${client.nom}" required>
                                    </div>
                                    <% if (request.getAttribute("NomTropCourtError") !=null) { %>
                                    <span style="color : red;"> Le nom doit contenir au moins 3 caracteres </span>
                                    <% } %>
                                </div>
                                <div class="form-group">
                                    <label for="first_name" class="col-sm-2 control-label">Prenom</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="first_name" name="first_name" value="${client.prenom}" required>
                                    </div>
                                    <% if (request.getAttribute("PrenomTropCourtError") !=null) { %>
                                    <span style="color : red;"> Le prenom doit contenir au moins 3 caracteres </span>
                                    <% } %>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">Email</label>

                                    <div class="col-sm-10">
                                        <input type="email" class="form-control" id="email" name="email" value="${client.email}" required>
                                    </div>
                                    <% if (request.getAttribute("EmailExistantError") !=null) { %>
                                    <span style="color : red;"> Le mail est deja utilise pour un autre compte </span>
                                    <% } %>
                                </div>
                                <div class="form-group">
                                    <label for="birthday" class="col-sm-2 control-label">Date de naissance</label>

                                    <div class="col-sm-10">
                                        <input type="date" class="form-control" id="birthday" name="birthday" value="${client.naissance}" required>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Modifier</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>
    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<script>
  var dateActuelle = new Date();
  var anneeMajeur = dateActuelle.getFullYear() - 18;
  var moisMajeur = dateActuelle.getMonth() + 1;
  var jourMajeur = dateActuelle.getDate();
  if (moisMajeur < 10) {
    moisMajeur = '0' + moisMajeur;
  }
  if (jourMajeur < 10) {
    jourMajeur = '0' + jourMajeur;
  }
  var dateMax = anneeMajeur + '-' + moisMajeur + '-' + jourMajeur;

  document.getElementById("birthday").setAttribute("max", dateMax);
</script>

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
