<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>ENIO AGENT</title>
  <!-- iCheck -->
  <link href="/webjars/gentelella/1.4.0/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
  <!-- Datatables -->
  <link href="/webjars/gentelella/1.4.0/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
  <link href="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
  <link href="/webjars/gentelella/1.4.0/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
  <link href="/webjars/gentelella/1.4.0/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
  <link href="/webjars/gentelella/1.4.0/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
  <style type="text/css">
    td.details-control {
      background: url('/images/details_open.png') no-repeat center center;
      cursor: pointer;
    }
    
    tr.shown td.details-control {
      background: url('/images/details_close.png') no-repeat center center;
    }
    
    tr.loading td {
      text-align: center;
    }
  </style>
</head>
<body>
  <!-- page content -->
  <div class="right_col" role="main">
    <div class="">
      <div class="page-title">
        <div class="title_left">
          <h3>송신 내역 <small></small></h3>
        </div>

        <!-- <div class="title_right">
          <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
            <div class="input-group">
              <input type="text" class="form-control" placeholder="Search for...">
              <span class="input-group-btn">
                <button class="btn btn-default" type="button">Go!</button>
              </span>
            </div>
          </div>
        </div> -->
      </div>

      <div class="clearfix"></div>

      <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
          <div class="x_panel">
            <div class="x_title">
              <h2>물품 <small>물품 송신 내역입니다.</small></h2>
              <ul class="nav navbar-right panel_toolbox">
                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
                <li><a href="javascript:location.reload(true);"><i class="fa fa-refresh"></i></a>
                <li><a class="close-link"><i class="fa fa-close"></i></a></li>
              </ul>
              <div class="clearfix"></div>
            </div>
            <div class="x_content">
              <div class="well" style="overflow: auto">
                <div class="row">
                  <div class="col-md-4 col-sm-4 col-xs-12">
                    <div class="form-group">
                      <label for="optE4uIfSt">송신상태</label>
                      <select id="optE4uIfSt" class="form-control">
                        <option value="">전체</option>
                        <c:forEach var="sendStatus" items="${sendStatuses}">
                            <option value="${sendStatus.code}">${sendStatus.codeNm}</option>
                        </c:forEach>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-4 col-sm-4 col-xs-12">
                    <div class="form-group">
                      <label for="">등록일</label>
                      <div class="input-prepend input-group">
                        <span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
                        <input type="text" style="width: 200px" name="inpRegDt" id="inpRegDt" class="form-control" value="" />
                      </div>
                    </div>
                  </div>
                </div>              
              </div>
              <table id="tblSendItems" class="table table-striped table-bordered table-hover" style="width: 100%">
                <thead>
                  <tr>
                    <th></th>
                    <th>ERP품목코드</th>
                    <th>품목명</th>
                    <th>규격</th>
                    <th>모델명</th>
                    <th>단위</th>
                    <th>사용유무</th>
                    <th>제조사</th>
                    <th>등록일시</th>
                    <th>전송상태</th>
                    <th>전송일시</th>
                    <th>품목코드</th>
                  </tr>
                </thead>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- /page content -->
  <content tag="script-external">
    <!-- Datatables -->
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/jszip/dist/jszip.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/pdfmake/build/vfs_fonts.js"></script>
    <script src="/js/jquery.spring-friendly.js"></script>
    <script src="/js/jsrender/jsrender.min.js"></script>
  </content>
  <script id="itemDetailTmpl" type="text/x-jsrender">
    <div class="x_panel">
      <table class="table table-bordered">
        <colgroup>
          <col style="width: 200px;">
            <col>
        </colgroup>
        <tbody>
          <tr>
            <th class="active text-right">사용유무</th>
            <td>{{:useYn}}</td>
          </tr>
          <tr>
            <th class="active text-right">전송일시</th>
            <td>{{:e4uIfDt}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </script>
  <content tag="script">
    <script>
      (function(enio, $, window, document) {
        var page = {
          load: function() {
            var $dtSendItems = (function() {
              var $table = $("#tblSendItems");
              var $optE4uIfSt = $("#optE4uIfSt");
              var $template = $.templates("#itemDetailTmpl");
              var $inpRegDt = $('#inpRegDt').daterangepicker({
                  locale: {
                    format: 'YYYY-MM-DD'
                  },
                  startDate: moment().subtract(6, 'months'),
                  endDate: moment(),
                  linkedCalendars: false,
                  showDropdowns: true
                },
                function(start, end, label) {
                  $dataTable.draw();
                });
  
              var $dataTable = $table.DataTable({
                processing: true,
                serverSide: true,
                ajax: {
                  url: "/send/items",
                  type: "GET",
                  data: function(d) {
                    var dataInpRegDt = $inpRegDt.data('daterangepicker');
                    d.e4uIfSt = $optE4uIfSt.val();
                    d.frRegDt = dataInpRegDt.startDate.format('YYYY-MM-DD');
                    d.toRegDt = dataInpRegDt.endDate.format('YYYY-MM-DD');
                  }
                },
                order: [
                  [8, "desc"]
                ],
                columns: [{
                  className: "details-control",
                  orderable: false,
                  data: null,
                  defaultContent: "",
                  width: "25"
                }, {
                  data: "erpItemCd",
                  name: "erp_item_cd"
                }, {
                  data: "itemNm",
                  name: "item_nm"
                }, {
                  data: "sizeNm",
                  name: "size_nm"
                }, {
                  data: "modelNm",
                  name: "model_nm"
                }, {
                  data: "unitCd",
                  name: "unit_cd"
                }, {
                  data: "useYn",
                  name: "use_yn",
                  visible: false
                }, {
                  data: "mkCompNm",
                  name: "mk_comp_nm"
                }, {
                  data: "regDt",
                  name: "reg_dt"
                }, {
                  data: "e4uIfSt",
                  name: "e4u_if_st",
                  render: function(data, type, row) {
                    var result = "대기";
                    if ("SW" === data) {
                      result = "송신 중";
                    } else if ("SWE" === data) {
                      result = "송신 오류";
                    } else if ("SF" === data) {
                      result = "송신 실패";
                    } else if ("SC" === data) {
                      result = "정상 완료";
                    }
                    return result;
                  }
                }, {
                  data: "e4uIfDt",
                  name: "e4u_if_dt",
                  visible: false
                }, {
                  data: "itemCd",
                  name: "item_cd"
                }],
                dom: "<'row'<'col-sm-6'l><'col-sm-6'f>>" +
                  "<'row'<'col-sm-12'B>>" +
                  "<'row'<'col-sm-12'tr>>" +
                  "<'row'<'col-sm-5'i><'col-sm-7'p>>",
                buttons: [{
                  extend: "reload",
                  className: "btn-sm"
                }]
              });
  
              function format(d) {
                var data = [{
                  "useYn": d.useYn,
                  "e4uIfDt": d.e4uIfDt
                }];
                return $template.render(data);
              }
  
              $table.find("tbody").on('click', 'td.details-control', function() {
                var tr = (this).closest('tr');
                var row = $dataTable.row(tr);
  
                if (row.child.isShown()) {
                  // This row is already open - close it
                  row.child.hide();
                  tr.removeClass('shown');
                } else {
                  // Open this row
                  row.child(format(row.data())).show();
                  tr.addClass('shown');
                }
              });
  
              $optE4uIfSt.on("change", function() {
                $dataTable.draw();
              });
  
              return $dataTable;
            }());
  
          }
        };
        $(page.load);
      }(window.enio || {}, window.jQuery, window, document));
    </script>
  </content>
</body>
</html>