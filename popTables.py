#Script to generate sql statements to populate database tables for 391 project.
#Hopefully we can use this to populate the database with test data.
#Still needs to have the pacs_images table added, however the other tables do not depend on its presence

#All the data are hardcoded here. Edit however you feel is necessary.

#Run this script from the command line and redirect it to a file
#e.g.
#     >>>python popTables.py > popT.sql

#To populate the data base, first execute the statements in the sql file on the assignment page.
#Then run the following in sqlplus to populate:
#     sql >@popT.sql


#data for persons table
'''
CREATE TABLE persons (
   person_id int,
   first_name varchar(24),
   last_name  varchar(24),
   address    varchar(128),
   email      varchar(128),
   phone      char(10),
   PRIMARY KEY(person_id),
   UNIQUE (email)
);
'''
person_ids = ["1","2","3","4","5","6","7","8","9","10","11","12"]

person_names = {"1":["'Keanu'","'Reeves'"],"2":["'Bill'","'Paxton'"],"3":["'Edward'","'Norton'"],"4":["'Karl'","'Urban'"],"5":["'Danny'","'Trejo'"],"6":["'Humphrey'","'Bogart'"],"7":["'Clint'","'Eastwood'"],
                "8":["'Milla'","'Jovovich'"],"9":["'Jennifer'","'Lawrence'"],"10":["'Jillian'","'Anderson'"],"11":["'Natalie'","'Portman'"],"12":["'Audrey'","'Hepburn'"]}

person_addresses = {"1":"'25 St'","2":"'5 Ave'","3":"'7 Blvd'","4":"'6 St'","5":"'8 Ave'","6":"'77 Cr'","7":"'99 St'",
                  "8":"'278 Ave'","9":"'46 Blvd'","10":"'67 St'","11":"'112 St'","12":"'80 Ave'"}

person_emails = {"1":"'P01@fakeEmail.com'","2":"'P02@fakeEmail.com'","3":"'P03@fakeEmail.com'","4":"'P04@fakeEmail.com'",
                 "5":"'P05@fakeEmail.com'","6":"'P06@fakeEmail.com'","7":"'P07@fakeEmail.com'","8":"'P08@fakeEmail.com'",
                 "9":"'P09@fakeEmail.com'","10":"'P10@fakeEmail.com'","11":"'P11@fakeEmail.com'","12":"'P12@fakeEmail.com'"}

person_phoneN = {"1":"'555-1234'","2":"'555-2345'","3":"'555-3456'","4":"'555-4567'","5":"'555-5678'","6":"'555-6789'",
                 "7":"'555-7890'","8":"'555-8901'","9":"'555-9012'","10":"'555-0123'","11":"'555-4321'","12":"'555-5432'"}

#data for users table
'''
CREATE TABLE users (
   user_name varchar(24),
   password  varchar(24),
   class     char(1),
   person_id int,
   date_registered date,
   CHECK (class in ('a','p','d','r')),
   PRIMARY KEY(user_name),
   FOREIGN KEY (person_id) REFERENCES persons
);
'''

usernames = {}
passwords = {}
classes = {"1":"'a'","2":"'p'","3":"'d'","4":"'r'","5":"'a'","6":"'p'","7":"'d'","8":"'r'","9":"'a'","10":"'p'","11":"'d'","12":"'r'"}
ids = {}
dates = {"1":"TO_DATE('20010702','YYYYMMDD')","2":"TO_DATE('20020506','YYYYMMDD')","3":"TO_DATE('20000108','YYYYMMDD')",
         "4":"TO_DATE('19790403','YYYYMMDD')","5":"TO_DATE('19850408','YYYYMMDD')",
         "6":"TO_DATE('19921023','YYYYMMDD')","7":"TO_DATE('19980629','YYYYMMDD')",
         "8":"TO_DATE('20000101','YYYYMMDD')","9":"TO_DATE('19991231','YYYYMMDD')",
         "10":"TO_DATE('19910722','YYYYMMDD')","11":"TO_DATE('19810417','YYYYMMDD')","12":"TO_DATE('09801126','YYYYMMDD')"}

for key in person_ids:  
    #username is first name with a 1 at the end
    usernames[key] = person_names[key][0][:-1]+"1'"
    #password is last name
    passwords[key] = person_names[key][1]
    #id is a person id
    ids[key] = key
    


#data for family_doctor table
'''
CREATE TABLE family_doctor (
   doctor_id    int,
   patient_id   int,
   FOREIGN KEY(doctor_id) REFERENCES persons,
   FOREIGN KEY(patient_id) REFERENCES persons,
   PRIMARY KEY(doctor_id,patient_id)
);
'''

famdoc = {"3":"2","7":"6","11":"10"}

#data for radiology_record table (6 rows)
'''
CREATE TABLE radiology_record (
   record_id   int,
   patient_id  int,
   doctor_id   int,
   radiologist_id int,
   test_type   varchar(24),
   prescribing_date date,
   test_date    date,
   diagnosis    varchar(128),
   description   varchar(1024),
   PRIMARY KEY(record_id),
   FOREIGN KEY(patient_id) REFERENCES persons,
   FOREIGN KEY(doctor_id) REFERENCES  persons,
   FOREIGN KEY(radiologist_id) REFERENCES  persons
);
'''
vals1 = "1001, 2, 3, 12, 'XRAY', TO_DATE('20081123','YYYYMMDD'), TO_DATE('20090213','YYYYMMDD'), 'Recto-cranial inversion.', 'Patient will require full frontal lobotomy.'"
vals2 = "1002, 2, 3, 12, 'ULTRASOUND', TO_DATE('20100517','YYYYMMDD'), TO_DATE('20100625','YYYYMMDD'), 'Spontaneous human combustion.', 'Ultrasound reveals that patient has become extremely flammable.'"
vals3 = "1003, 6, 7, 4, 'CT/CAT', TO_DATE('20130812','YYYYMMDD'), TO_DATE('20140316','YYYYMMDD'), 'Mutation due to alien experimentation.', 'Patient is highly radioactive, abandon radiology laboratory.'"
vals4 = "1004, 6, 7, 4, 'MRI', TO_DATE('20140915','YYYYMMDD'), TO_DATE('20150107','YYYYMMDD'), 'Human-alien hybridization.', 'Notify special agents Mulder and Scully immediately.'"
vals5 = "1005, 10, 11, 8, 'XRAY', TO_DATE('20041202','YYYYMMDD'), TO_DATE('20041203','YYYYMMDD'), 'Fractured third metacarpal.', 'Patient placed hand in the mouth of an alligator. Recommend cranial amputation.'"
vals6 = "1006, 10, 11, 8, 'ULTRASOUND', TO_DATE('20061031','YYYYMMDD'), TO_DATE('20070302','YYYYMMDD'), 'Overdose on halloween candy.', 'Patient will require removal of both legs.'"

radrec = [vals1,vals2,vals3,vals4,vals5,vals6]

#Table generation
for key in person_ids:
    #persons table
    print("INSERT INTO persons VALUES("+key+","+person_names[key][0]+","+person_names[key][1]+","+person_addresses[key]+","+person_emails[key]+","+person_phoneN[key]+");")
    
for key in person_ids:
    #users table
    print("INSERT INTO users VALUES("+usernames[key]+","+passwords[key]+","+classes[key]+","+key+","+dates[key]+");")

for key in famdoc:
    #family_doctor table
    print("INSERT INTO family_doctor VALUES("+key+","+famdoc[key]+");")
    
for rec in radrec:
    #radiology_record table
    print("INSERT INTO radiology_record values("+rec+");")
