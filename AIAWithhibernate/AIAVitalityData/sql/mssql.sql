
--run For mssql
CREATE TABLE HK_ACHIEVE_GOLD (
  HK_ACHIEVE_GOLD_ID int IDENTITY(1,1) PRIMARY KEY,
  MEMBER_FIRST_NAMES VARCHAR(25) NULL,
  MEMBER_SURNAME VARCHAR(25) NULL,
  EMAIL_ADDRESS VARCHAR(70) NULL,
  AIA_VITALITY_MEMBER_NUMBER VARCHAR(25) NULL,
  ENTITY_REFERENCE_NUMBER VARCHAR(25) NULL,
  VITALITY_STATUS VARCHAR(25) NULL,
  POINTS_BALANCE VARCHAR(25) NULL,
  GENDER VARCHAR(25) NULL,
  CLIENT_ID VARCHAR(25) NULL,
  LANGUAGE_PREFERENCE VARCHAR(25) NULL,
  RECORD_STATUS VARCHAR(2) NULL,
  PROCESS_DATE DATE NULL,
  RECORD_ID VARCHAR(25) NULL,
  FILE_TYPE VARCHAR(25) NULL,
  LAST_MODIFIED_DATE DATE NULL,
  FILE_NAME VARCHAR(255) NULL);
  
  
  CREATE TABLE PROCESSED_FILE (
  FILE_ID int IDENTITY(1,1) PRIMARY KEY,
  FILE_NAME VARCHAR(255) NULL,
  PROCESS_DATE DATE NULL,
  ELOQUA_PROCESS_DATE DATE NULL,
  DUPLICATE_RECORDS int,
  TOTAL_RECORDS int);
  
  
  CREATE TABLE ELOQUA (
  ID int IDENTITY(1,1) PRIMARY KEY,
  SITE VARCHAR(70) NULL,
  USER_NAME VARCHAR(70) NULL,
  PASS_WORD VARCHAR(70) NULL,
  BASE_URL VARCHAR(70) NULL,
  MODIFIED_BY VARCHAR(70) NULL);
  
  CREATE TABLE FTP (
  ID int IDENTITY(1,1) PRIMARY KEY,
  HOST VARCHAR(70) NULL,
  USER_NAME VARCHAR(70) NULL,
  PASS_WORD VARCHAR(70) NULL,
  MODIFIED_BY VARCHAR(70) NULL);
  
   
  CREATE TABLE CUSTOM_OBJECT_ID (
  ID int IDENTITY(1,1) PRIMARY KEY,
  FILE_TYPE VARCHAR(70) NULL,
  CUSTOME_ID VARCHAR(70) NULL);
  
  CREATE TABLE CUSTOM_FIELD (
  ID int IDENTITY(1,1) PRIMARY KEY,
  FILE_TYPE VARCHAR(70) NULL,
  FIELD_NAME VARCHAR(70) NULL,
  FIELD_ID VARCHAR(70) NULL);
  
  
 insert into ELOQUA (SITE, USER_NAME, PASS_WORD, BASE_URL, MODIFIED_BY) values ('AIA', 'Ramya.Ponnusamy', 'Verticurl2014!', 'https://secure.eloqua.com/API', 'SCRIPT');
 insert into FTP (HOST, USER_NAME, PASS_WORD, MODIFIED_BY) values ('0343825.netsolhost.com', 'verticurl_aia', 'Passw0rd2015', 'SCRIPT');
  
 insert into CUSTOM_OBJECT_ID (FILE_TYPE, CUSTOME_ID) values ('HKAG','15');
 insert into CUSTOM_FIELD (FILE_TYPE,  FIELD_NAME, FIELD_ID) values ('HKAG', 'LANGUAGE_PREFERENCE1', '367');
 insert into CUSTOM_FIELD (FILE_TYPE,  FIELD_NAME, FIELD_ID) values ('HKAG', 'CLIENT_ID1', '368');
 insert into CUSTOM_FIELD (FILE_TYPE,  FIELD_NAME, FIELD_ID) values ('HKAG', 'GENDER1', '369');
 insert into CUSTOM_FIELD (FILE_TYPE,  FIELD_NAME, FIELD_ID) values ('HKAG', 'POINTS_BALANCE1', '370');
 insert into CUSTOM_FIELD (FILE_TYPE,  FIELD_NAME, FIELD_ID) values ('HKAG', 'VITALITY_STATUS1', '371');
 insert into CUSTOM_FIELD (FILE_TYPE,  FIELD_NAME, FIELD_ID) values ('HKAG', 'ENTITY_REFERENCE_NUMBER1', '372');
 insert into CUSTOM_FIELD (FILE_TYPE,  FIELD_NAME, FIELD_ID) values ('HKAG', 'AIA_VITALITY_MEMBER_NUMBER1', '373');
 insert into CUSTOM_FIELD (FILE_TYPE,  FIELD_NAME, FIELD_ID) values ('HKAG', 'EMAIL_ADDRESS1', '374');
 insert into CUSTOM_FIELD (FILE_TYPE,  FIELD_NAME, FIELD_ID) values ('HKAG', 'MEMBER_SURNAME1', '375');
 insert into CUSTOM_FIELD (FILE_TYPE,  FIELD_NAME, FIELD_ID) values ('HKAG', 'MEMBER_FIRST_NAMES1', '376');