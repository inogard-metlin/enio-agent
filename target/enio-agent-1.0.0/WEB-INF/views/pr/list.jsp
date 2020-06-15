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
          <h3>수신 내역 <small></small></h3>
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
              <h2>구매요청 <small>구매요청 수신 내역입니다.</small></h2>
              <ul class="nav navbar-right panel_toolbox">
                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
                <li><a href="javascript:location.reload(true);"><i class="fa fa-refresh"></i></a>
                <li><a class="close-link"><i class="fa fa-close"></i></a></li>
              </ul>
              <div class="clearfix"></div>
            </div>
            <div class="x_content">
              <div class="well" style="overflow: auto">
                <div class="form-horizontal">
                  <div class="form-group">
                    <label class="control-label col-md-2 col-sm-2 col-xs-12">전송상태 : </label>
                    <div class="col-md-4 col-sm-4 col-xs-12">
                      <select id="optE4uIfSt" class="form-control">
                        <option value="">전체</option>
                        <c:forEach var="sendStatus" items="${sendStatuses}">
                          <option value="${sendStatus.code}"><c:out value="${sendStatus.codeNm}" /></option>
                        </c:forEach>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <table id="tblPr" class="table table-striped table-bordered table-hover" style="width: 100%">
                <thead>
                  <tr>
                    <th></th>
                    <th>구매요청번호</th>
                    <th>요청명</th>
                    <th>구분</th>
                    <th>과세</th>
                    <th>내외자</th>
                    <th>입찰종류1</th>
                    <th>입찰종류2</th>
                    <th>구매담당자</th>
                    <th>등록일시</th>
                    <th>구매요청번호</th>
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
  
  <script id="prDetailTmpl" type="text/x-jsrender">
    <div class="x_panel">
      <table class="table table-bordered">
        <colgroup>
          <col style="width: 200px;" />
          <col />
          <col style="width: 200px;" />
          <col />
        </colgroup>
        <tbody>
          <tr>
            <th class="active text-right">납품요구일</th>
            <td colspan="3">{{:dlvReqYmd}}</td>
          </tr>
          <tr>
            <th class="active text-right">납품장소</th>
            <td colspan="3">{{:dlvLoc}}</td>
          </tr>
          <tr>
            <th class="active text-right">현장설명유무</th>
            <td>{{:spotDscrYn}}</td>
            <th class="active text-right">현장설명일시</th>
            <td>{{:spotDscrDt}}</td>
          </tr>
          <tr>
            <th class="active text-right">현장설명 담당자</th>
            <td>{{:spotDscrUsrnm}}</td>
            <th class="active text-right">현장설명 담당자 전화번호</th>
            <td>{{:spotDscrDsrtel}}</td>
          </tr>
          <tr>
            <th class="active text-right">현장설명장소</th>
            <td colspan="3">{{:spotDscrLoc}}</td>
          </tr>
        </tbody>
      </table>
      <div id="wrapSendPrItems">
        <div class="x_title">
          <h4>구매물품정보</h4>
          <div class="clearfix"></div>
        </div>
        <table id="tblPrItems" class="table table-striped table-bordered table-hover" style="width: 100%">
          <thead>
            <tr>
              <th>구매요청번호</th>
              <th>품목순번</th>
              <th>품목명</th>
              <th>자재코드</th>
              <th>ERP자재코드</th>
              <th>분류코드</th>
              <th>규격</th>
              <th>모델명</th>
              <th>납품장소</th>
              <th>납품요구일</th>
              <th>제조사</th>
              <th>단위</th>
              <th>수량</th>
              <th>예정단가</th>
            </tr>
          </thead>
        </table>
      </div>
      <div id="wrapSendPrServices">
        <div class="x_title">
          <h4>시설/공사(용역)정보</h4>
          <div class="clearfix"></div>
        </div>
        <table id="tblPrServices" class="table table-striped table-bordered table-hover" style="width: 100%">
          <thead>
            <tr>
              <th>구매요청번호</th>
              <th>품목순번</th>
              <th>공종순번</th>
              <th>공종명</th>
              <th>공종코드</th>
              <th>단위</th>
              <th>수량</th>
            </tr>
          </thead>
        </table>
      </div>
      <div class="x_title">
        <h4>첨부파일</h4>
        <div class="clearfix"></div>
      </div>
      <table id="tblPrFiles" class="table table-striped table-bordered table-hover" style="width: 100%">
        <thead>
          <tr>
            <th>구매요청번호</th>
            <th>순번</th>
            <th>파일번호</th>
            <th>파일명</th>
            <th>서버파일명</th>
            <th>파일경로</th>
            <th>등록일시</th>
            <th>ERP구매요청번호</th>
          </tr>
        </thead>
      </table>
    </div>
  </script>
  <content tag="script">
    <script>
      (function(enio, $, window, document) {
        var page = {
          load: function() {
            var dtPr = (function() {
              var $dataTable = null;
              var $template = $.templates("#prDetailTmpl");
  
              var init = function(table) {
                var $table = table ? table : $("#tblPr");
                var $optE4uIfSt = $("#optE4uIfSt");
  
                $dataTable = $table.DataTable({
                  processing: true,
                  serverSide: true,
                  ajax: {
                    url: "/pr",
                    data: function(d) {
                      d.e4uIfSt = $optE4uIfSt.val();
                    }
                  },
                  order: [
                    [9, "desc"]
                  ],
                  columns: [{
                    className: "details-control",
                    orderable: false,
                    data: null,
                    defaultContent: "",
                    width: "25"
                  }, {
                    data: "prNo",
                    name: "pr_no"
                  }, {
                    data: "ttl",
                    name: "ttl"
                  }, {
                    data: "prTypecd",
                    name: "pr_typecd"
                  }, {
                    data: "taxTypecd",
                    name: "tax_typecd"
                  }, {
                    data: "ioFlag",
                    name: "io_flag"
                  }, {
                    data: "bidSubTypecd",
                    name: "bid_sub_typecd"
                  }, {
                    data: "bidTypecd",
                    name: "bid_typecd"
                  }, {
                    data: "gmChrgrCd",
                    name: "gm_chrgr_cd"
                  }, {
                    data: "regDt",
                    name: "reg_dt"
                  }, {
                    data: "erpPrNo",
                    name: "erp_pr_no"
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
  
                $table.find("tbody").on('click', 'td.details-control', function() {
                  var tr = $(this).closest('tr');
                  var row = $dataTable.row(tr);
  
                  if (row.child.isShown()) {
                    // This row is already open - close it
                    row.child.hide();
                    tr.removeClass('shown');
                  } else {
                    // Open this row
                    row.child(_format(row.data())).show();
                    tr.addClass('shown');
                  }
                });
  
                $optE4uIfSt.on("change", function() {
                  $dataTable.draw();
                });
              };
  
              var _format = function(d) {
                var $htmlOutput = $('<div/>').addClass('loading').text('Loading...');
                var reqPr = $.ajax("/pr/" + d.prNo, {
                  dataType: "json"
                });
  
                reqPr.then(function(data) {
                  console.log("Request Pr Done");
                  var $renderedHtml = $($template.render(data));
                  if (d.prTypecd === "N") {
                    $renderedHtml.find("#wrapSendPrServices").addClass("hidden");
                  } else {
                    $renderedHtml.find("#wrapSendPrItems").addClass("hidden");
                  }
  
                  $htmlOutput.html($renderedHtml).removeClass('loading');
                  return $.ajax("/pr/" + d.prNo + "/items", {
                    dataType: "json"
                  });
                }).then(function(data) {
                  console.log("Request Pr Items Done");
                  dtPrItems.init($htmlOutput.find("#tblPrItems"), {
                    data: data
                  });
  
                  var reqPrServices = null;
                  if (d.prTypecd !== "N") {
                    reqPrServices = $.ajax("/pr/" + d.prNo + "/items/" + data[0].itemSeq + "/services", {
                      dataType: "json"
                    });
                  }
  
                  return $.when(reqPrServices);
                }).then(function(data) {
                  console.log("Request Pr Services Done");
                  if (data !== null) {
                    dtPrServices.init($htmlOutput.find("#tblPrServices"), {
                      data: data
                    });
                  }
                  return $.ajax("/pr/" + d.prNo + "/files", {
                    dataType: "json"
                  });
                }).done(function(data) {
                  console.log("Request Pr Files Done");
                  dtPrFiles.init($htmlOutput.find("#tblPrFiles"), {
                    data: data
                  });
                }).fail(function(jqXHR, textStatus) {
                  alert("Request failed: " + textStatus);
                });
  
                return $htmlOutput;
              };
  
              return {
                init: init,
                getDataTable: $dataTable
              }
            }());
  
            var dtPrItems = (function() {
              var $dataTable = null;
              var init = function(table, opt) {
                var $table = table ? table : $("#tblPrItems");
                var settings = {
                  lengthChange: false,
                  paging: false,
                  searching: false,
                  info: false,
                  columns: [{
                    data: "prNo",
                    name: "pr_no",
                    visible: false
                  }, {
                    data: "itemSeq",
                    name: "item_seq",
                    visible: false
                  }, {
                    data: "itemNm",
                    name: "item_nm"
                  }, {
                    data: "itemCd",
                    name: "item_cd",
                    visible: false
                  }, {
                    data: "erpItemCd",
                    name: "erp_item_cd",
                    visible: false
                  }, {
                    data: "clsCd",
                    name: "cls_cd",
                    visible: false
                  }, {
                    data: "sizeNm",
                    name: "size_nm"
                  }, {
                    data: "modelNm",
                    name: "model_nm"
                  }, {
                    data: "dlvLoc",
                    name: "dlv_loc"
                  }, {
                    data: "dlvReqYmd",
                    name: "dlv_req_ymd"
                  }, {
                    data: "mkCompNm",
                    name: "mk_comp_nm"
                  }, {
                    data: "unitCd",
                    name: "unit_cd"
                  }, {
                    data: "qty",
                    name: "qty",
                    render: $.fn.dataTable.render.number(',', '.', 0),
                    className: "text-right"
                  }, {
                    data: "planUnitPrc",
                    name: "plan_unit_prc",
                    render: $.fn.dataTable.render.number(',', '.', 0),
                    className: "text-right"
                  }]
                };
  
                $.extend(settings, opt);
                $dataTable = $table.DataTable(settings);
              };
  
              return {
                init: init,
                getDataTable: $dataTable
              }
            }());
  
            var dtPrServices = (function() {
              var $dataTable = null;
              var init = function(table, opt) {
                var $table = table ? table : $("#tblPrServices");
                var settings = {
                  lengthChange: false,
                  paging: false,
                  searching: false,
                  info: false,
                  columns: [{
                    data: "prNo",
                    name: "pr_no",
                    visible: false
                  }, {
                    data: "itemSeq",
                    name: "item_seq",
                    visible: false
                  }, {
                    data: "srvSeq",
                    name: "srv_seq",
                    visible: false
                  }, {
                    data: "srvNm",
                    name: "srv_nm"
                  }, {
                    data: "srvCd",
                    name: "srv_cd"
                  }, {
                    data: "unitCd",
                    name: "unit_cd"
                  }, {
                    data: "qty",
                    name: "qty",
                    render: $.fn.dataTable.render.number(',', '.', 0)
                  }]
                };
  
                $.extend(settings, opt);
                $dataTable = $table.DataTable(settings);
              };
  
              return {
                init: init,
                getDataTable: $dataTable
              }
            }());
            
            var dtPrFiles = (function() {
              var $dataTable = null;
              var init = function(table, opt) {
                var $table = table ? table : $("#tblPrFiles");
                var settings = {
                  lengthChange: false,
                  paging: false,
                  searching: false,
                  info: false,
                  columns: [{
                    data: "prNo",
                    name: "pr_no",
                    visible: false
                  }, {
                    data: "fileSeq",
                    name: "file_seq",
                    width: "10%"
                  }, {
                    data: "fileNo",
                    name: "file_no",
                    visible: false
                  }, {
                    data: "cliFileNm",
                    name: "cli_file_mm",
                    render: function(data, type, row, meta) {
                      var url = "/pr/" + row.prNo + "/files/" + row.fileSeq;
                      return (type === 'display') ? '<a href="' + url + '">' + data + '</a>' : data;
                    }
                  }, {
                    data: "svrFileNm",
                    name: "svr_file_nm",
                    visible: false
                  }, {
                    data: "svrFilePath",
                    name: "svr_file_path",
                    visible: false
                  }, {
                    data: "regDt",
                    name: "reg_dt",
                    visible: false
                  }, {
                    data: "erpPrNo",
                    name: "erp_pr_no",
                    visible: false
                  }]
                };
  
                $.extend(settings, opt);
                $dataTable = $table.DataTable(settings);
              };
  
              return {
                init: init,
                getDataTable: $dataTable
              }
            }());
  
            dtPr.init();
          }
        };
        $(page.load);
      }(window.enio || {}, window.jQuery, window, document));
    </script>
  </content>
</body>
</html>