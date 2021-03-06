--{####테스트자료#####
-- 자재(Dummy)
INSERT INTO AGT_DUM_ITEM(ERP_ITEM_CD,ITEM_NM,CLS_CD,SIZE_NM,MODEL_NM,MK_COMP_NM,UNIT_CD,USE_YN,REG_DT)
SELECT 'ITM00009999010100001','노트북','999999','14inch','XNote DR500','LG전자','EA','Y','20171001090909' FROM DUAL
UNION ALL SELECT 'ITM00009999010100002','마우스','999999','소형','블루투스 M15','LG인터네셔널','EA','Y','20171001090909' FROM DUAL
UNION ALL SELECT 'ITM00009999010100003','키보드','999999','101','블루투스 K15','LG인터네셔널','EA','Y','20171001090909' FROM DUAL
UNION ALL SELECT 'ITM00009999010100004','외장하드','999999','2TB','WD 2Tbytes','Wester Digital','EA','Y','20171001090909' FROM DUAL;

-- {구매요청관련(Dummy)
--  기본(Dummy) :(황)노트북외2건 구매
INSERT INTO AGT_DUM_PR(ERP_PR_NO,TTL,PR_TYPECD,IO_FLAG,TAX_TYPECD,REQ_USR_NM,REQ_USR_TEL,DLV_LOC,DLV_REQ_YMD,SPOT_DSCR_YN,SPOT_DSCR_DT,SPOT_DSCR_USRNM,SPOT_DSCR_USRTEL,SPOT_DSCR_LOC,REG_DT,PR_RMRK,PREBID_YN,ERP_RFQ_NO,BID_SUB_TYPECD,BID_TYPECD)
SELECT 'ERPPR9999010100001','(황)노트북외2건 구매','N','D','A1000','황의돈','02-869-2730','수학과 과사무실','20171130','Y','20171101090000','황의돈','02-869-2730','수학과 과사무실','20171026090909','비고사항','N','ERPRFQ2017090900001','4','1' FROM DUAL;
--  품목내역(Dummy)
INSERT INTO AGT_DUM_PRITEM(ERP_PR_NO,ITEM_SEQ,ERP_ITEM_CD,ITEM_NM,CLS_CD,SIZE_NM,MODEL_NM,MK_COMP_NM,DLV_LOC,DLV_REQ_YMD,UNIT_CD,QTY,PLAN_UNIT_PRC)
SELECT 'ERPPR9999010100001','00001','ITM00009999010100001','노트북','999999','14inch','XNote DR500','LG전자','수학과 과사무실','20171130','EA',1,1500000 FROM DUAL
UNION ALL SELECT 'ERPPR9999010100001','00002','ITM00009999010100002','마우스','999999','소형','블루투스 M15','LG인터네셔널','수학과 과사무실','20171130','EA',2,20000 FROM DUAL
UNION ALL SELECT 'ERPPR9999010100001','00003','ITM00009999010100003','키보드','999999','101','블루투스 K15','LG인터네셔널','수학과 과사무실','20171130','EA',3,12000 FROM DUAL;
--  첨부파일(Dummy)
INSERT INTO AGT_DUM_PRFILE(ERP_PR_NO,FILE_SEQ,SVR_FILE_NM,SVR_FILE_PATH)
SELECT 'ERPPR9999010100001','00001','구매사양서.txt','c:/TESTFILES/ERPPR9999010100001' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100001','00002','입찰참가신청서.txt','c:/TESTFILES/ERPPR9999010100001' FROM DUAL;
--  담당자(Dummy)
INSERT INTO AGT_DUM_PRUSER(ERP_PR_NO,USER_KIND,USER_NM,USER_EMAIL,USER_TEL,USER_SMS,DEPT_NM)
SELECT 'ERPPR9999010100001','GM','황의돈','nirvana7304@inogard.co.kr','02-869-2730','010-2721-7317','총괄지원팀' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100001','PR','황의돈PR','pr@a.com','02-869-2730','010-2721-7317','수학과' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100001','GR','황의돈GR','gr@a.com','02-869-2730','010-2721-7317','수학과' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100001','CHK','황의돈CHK','chk@a.com','02-869-2730','010-2721-7317','시설팀' FROM DUAL;

--  기본(Dummy) : (황)외장하드외1건 구매
INSERT INTO AGT_DUM_PR(ERP_PR_NO,TTL,PR_TYPECD,IO_FLAG,TAX_TYPECD,REQ_USR_NM,REQ_USR_TEL,DLV_LOC,DLV_REQ_YMD,SPOT_DSCR_YN,SPOT_DSCR_DT,SPOT_DSCR_USRNM,SPOT_DSCR_USRTEL,SPOT_DSCR_LOC,REG_DT,PR_RMRK,PREBID_YN,ERP_RFQ_NO,BID_SUB_TYPECD,BID_TYPECD)
SELECT 'ERPPR9999010100002','(황)외장하드외1건 구매','N','D','A1000','황의돈','02-869-2730','수학과 과사무실','20171130','Y','20171101090000','황의돈','02-869-2730','수학과 과사무실','20171026090909','비고사항','N','ERPRFQ2017090900001','4','1' FROM DUAL;
--  품목내역(Dummy)
INSERT INTO AGT_DUM_PRITEM(ERP_PR_NO,ITEM_SEQ,ERP_ITEM_CD,ITEM_NM,CLS_CD,SIZE_NM,MODEL_NM,MK_COMP_NM,DLV_LOC,DLV_REQ_YMD,UNIT_CD,QTY,PLAN_UNIT_PRC)
SELECT 'ERPPR9999010100002','00001','ITM00009999010100004','외장하드','999999','2TB','WD 2TBytes','Western Digital','수학과 과사무실','20171130','EA',1,120000 FROM DUAL
UNION ALL SELECT 'ERPPR9999010100002','00003','ITM00009999010100003','키보드','999999','101','블루투스 K15','LG인터네셔널','수학과 과사무실','20171130','EA',3,12000 FROM DUAL;
--  첨부파일(Dummy)
INSERT INTO AGT_DUM_PRFILE(ERP_PR_NO,FILE_SEQ,SVR_FILE_NM,SVR_FILE_PATH)
SELECT 'ERPPR9999010100002','00001','구매사양서.txt','c:/TESTFILES/ERPPR9999010100002' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100002','00002','입찰참가신청서.txt','c:/TESTFILES/ERPPR9999010100002' FROM DUAL;
--  담당자(Dummy)
INSERT INTO AGT_DUM_PRUSER(ERP_PR_NO,USER_KIND,USER_NM,USER_EMAIL,USER_TEL,USER_SMS,DEPT_NM)
SELECT 'ERPPR9999010100002','GM','황의돈','nirvana7304@inogard.co.kr','02-869-2730','010-2721-7317','총괄지원팀' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100002','PR','황의돈PR','pr@a.com','02-869-2730','010-2721-7317','수학과' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100002','GR','황의돈GR','gr@a.com','02-869-2730','010-2721-7317','수학과' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100002','CHK','황의돈CHK','chk@a.com','02-869-2730','010-2721-7317','시설팀' FROM DUAL;

--  기본(Dummy) : (황)마우스외1건 구매
INSERT INTO AGT_DUM_PR(ERP_PR_NO,TTL,PR_TYPECD,IO_FLAG,TAX_TYPECD,REQ_USR_NM,REQ_USR_TEL,DLV_LOC,DLV_REQ_YMD,SPOT_DSCR_YN,SPOT_DSCR_DT,SPOT_DSCR_USRNM,SPOT_DSCR_USRTEL,SPOT_DSCR_LOC,REG_DT,PR_RMRK,PREBID_YN,ERP_RFQ_NO,BID_SUB_TYPECD,BID_TYPECD)
SELECT 'ERPPR9999010100003','(황)마우스외1건 구매','N','D','A1000','황의돈','02-869-2730','수학과 과사무실','20171130','Y','20171101090000','황의돈','02-869-2730','수학과 과사무실','20171026090909','비고사항','N','ERPRFQ2017090900001','4','1' FROM DUAL;
--  품목내역(Dummy)
INSERT INTO AGT_DUM_PRITEM(ERP_PR_NO,ITEM_SEQ,ERP_ITEM_CD,ITEM_NM,CLS_CD,SIZE_NM,MODEL_NM,MK_COMP_NM,DLV_LOC,DLV_REQ_YMD,UNIT_CD,QTY,PLAN_UNIT_PRC)
SELECT 'ERPPR9999010100003','00001','ITM00009999010100002','마우스','999999','소형','블루투스 M15','LG인터네셔널','수학과 과사무실','20171130','EA',2,20000 FROM DUAL
UNION ALL SELECT 'ERPPR9999010100003','00002','ITM00009999010100003','키보드','999999','101','블루투스 K15','LG인터네셔널','수학과 과사무실','20171130','EA',3,12000 FROM DUAL;
--  첨부파일(Dummy)
INSERT INTO AGT_DUM_PRFILE(ERP_PR_NO,FILE_SEQ,SVR_FILE_NM,SVR_FILE_PATH)
SELECT 'ERPPR9999010100003','00001','구매사양서.txt','c:/TESTFILES/ERPPR9999010100003' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100003','00002','입찰참가신청서.txt','c:/TESTFILES/ERPPR9999010100003' FROM DUAL;
--  담당자(Dummy)
INSERT INTO AGT_DUM_PRUSER(ERP_PR_NO,USER_KIND,USER_NM,USER_EMAIL,USER_TEL,USER_SMS,DEPT_NM)
SELECT 'ERPPR9999010100003','GM','황의돈','nirvana7304@inogard.co.kr','02-869-2730','010-2721-7317','총괄지원팀' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100003','PR','황의돈PR','pr@a.com','02-869-2730','010-2721-7317','수학과' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100003','GR','황의돈GR','gr@a.com','02-869-2730','010-2721-7317','수학과' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100003','CHK','황의돈CHK','chk@a.com','02-869-2730','010-2721-7317','시설팀' FROM DUAL;

--  기본(Dummy) : (황)키보드 구매
INSERT INTO AGT_DUM_PR(ERP_PR_NO,TTL,PR_TYPECD,IO_FLAG,TAX_TYPECD,REQ_USR_NM,REQ_USR_TEL,DLV_LOC,DLV_REQ_YMD,SPOT_DSCR_YN,SPOT_DSCR_DT,SPOT_DSCR_USRNM,SPOT_DSCR_USRTEL,SPOT_DSCR_LOC,REG_DT,PR_RMRK,PREBID_YN,ERP_RFQ_NO,BID_SUB_TYPECD,BID_TYPECD)
SELECT 'ERPPR9999010100004','(황)키보드 구매','N','D','A1000','황의돈','02-869-2730','수학과 과사무실','20171130','Y','20171101090000','황의돈','02-869-2730','수학과 과사무실','20171026090909','비고사항','N','ERPRFQ2017090900001','4','1' FROM DUAL;
--  품목내역(Dummy)
INSERT INTO AGT_DUM_PRITEM(ERP_PR_NO,ITEM_SEQ,ERP_ITEM_CD,ITEM_NM,CLS_CD,SIZE_NM,MODEL_NM,MK_COMP_NM,DLV_LOC,DLV_REQ_YMD,UNIT_CD,QTY,PLAN_UNIT_PRC)
SELECT 'ERPPR9999010100004','00001','ITM00009999010100003','키보드','999999','101','블루투스 K15','LG인터네셔널','수학과 과사무실','20171130','EA',3,12000 FROM DUAL;
--  첨부파일(Dummy)
INSERT INTO AGT_DUM_PRFILE(ERP_PR_NO,FILE_SEQ,SVR_FILE_NM,SVR_FILE_PATH)
SELECT 'ERPPR9999010100004','00001','구매사양서.txt','c:/TESTFILES/ERPPR9999010100004' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100004','00002','입찰참가신청서.txt','c:/TESTFILES/ERPPR9999010100004' FROM DUAL;
--  담당자(Dummy)
INSERT INTO AGT_DUM_PRUSER(ERP_PR_NO,USER_KIND,USER_NM,USER_EMAIL,USER_TEL,USER_SMS,DEPT_NM)
SELECT 'ERPPR9999010100004','GM','황의돈','nirvana7304@inogard.co.kr','02-869-2730','010-2721-7317','총괄지원팀' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100004','PR','황의돈PR','pr@a.com','02-869-2730','010-2721-7317','수학과' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100004','GR','황의돈GR','gr@a.com','02-869-2730','010-2721-7317','수학과' FROM DUAL
UNION ALL SELECT 'ERPPR9999010100004','CHK','황의돈CHK','chk@a.com','02-869-2730','010-2721-7317','시설팀' FROM DUAL;