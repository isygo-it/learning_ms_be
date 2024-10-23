-- ims
INSERT INTO ims.t_domain (check_cancel, canceled_by, cancel_date, created_by, create_date, id, updated_by, update_date,
                          admin_status, company_logo, name, url, description, kmsDomain, code)
VALUES (false, NULL, NULL, 0, NULL, 1, 0, NULL, 'ENABLED', NULL, 'novobit.eu', NULL, NULL, NULL, 'RDOM000000');

INSERT INTO ims.t_account_details (created_by, create_date, id, updated_by, update_date, country_name, first_name,
                                   last_name)
VALUES (0, NULL, 1, 0, NULL, 'Tunisia', 'Sami', 'Mbarki');

INSERT INTO ims.t_account (check_cancel, account_details_id, canceled_by, cancel_date, created_by, create_date, id,
                           updated_by, update_date, admin_status, code, system_status, email, kmsDomain, phone_number,
                           auth_type, language_code)
VALUES (false, 1, NULL, NULL, 0, '2023-09-06 15:15:49.678', 1, 0, NULL, 'ENABLED', 'root',
        'IDLE', 's.mbarki@novobit.eu', 'novobit.eu', '0021653579452', 'OTP', 'EN');

-- kms
INSERT INTO kms.t_domain (check_cancel, canceled_by, cancel_date, created_by, create_date, id, updated_by, update_date,
                          admin_status, name, url, description)
VALUES (false, NULL, NULL, 0, NULL, 1, 0, '2023-09-06 15:01:23.088', 'DISABLED',
        'novobit.eu', NULL, NULL);

INSERT INTO kms.t_account (created_by, create_date, id, updated_by, update_date, code, email, kmsDomain)
VALUES (0, NULL, 1, 0, NULL, 'root', 's.mbarki@novobit.eu',
        'novobit.eu');
