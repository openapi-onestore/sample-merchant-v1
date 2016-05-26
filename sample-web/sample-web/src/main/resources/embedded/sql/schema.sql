CREATE TABLE file_payment_table (
  `mid` varchar(45) NOT NULL,
  `billing_token` varchar(45) DEFAULT NULL,
  `product_id` varchar(45) DEFAULT NULL,
  `product_name` varchar(45) DEFAULT NULL,
  `order_no` varchar(45) DEFAULT NULL,
  `amt_request_purchase` varchar(45) DEFAULT NULL,
  `amt_carrier` varchar(45) DEFAULT NULL,
  `amt_credit_card` varchar(45) DEFAULT NULL,
  `amt_tms` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`mid`)
);

CREATE TABLE file_payment_request (
  `mid` bigint NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `result_code` varchar(10) DEFAULT NULL,
  `result_msg` varchar(45) DEFAULT NULL,
  `waiting_jobs` varchar(45) DEFAULT NULL,
  `job_id` varchar(45) NOT NULL,
  `upload_file` varchar(45) DEFAULT NULL,
  `upload_date` varchar(45) DEFAULT NULL,
  `verify_message` varchar(45) DEFAULT NULL,  
  PRIMARY KEY (`mid`)
);

CREATE TABLE file_payment_noti_result (
  `job_id` varchar(45) NOT NULL,
  `event` varchar(45) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `update_date` varchar(45) DEFAULT NULL,
  `noti_version` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`job_id`)
);

ALTER TABLE file_payment_request ALTER COLUMN mid bigint AUTO_INCREMENT;
