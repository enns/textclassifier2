# Text classifier 2
[![Build Status](https://travis-ci.org/ripreal/textclassifier2.svg?branch=master)](https://travis-ci.org/ripreal/textclassifier2)
[![codecov](https://codecov.io/gh/ripreal/textclassifier2/branch/master/graph/badge.svg)](https://codecov.io/gh/ripreal/textclassifier2)
[![BCH compliance](https://bettercodehub.com/edge/badge/ripreal/textclassifier2?branch=master)](https://bettercodehub.com/)
[![Join the chat at https://gitter.im/V8LogScannerWeb/Lobby?source=orgpage](https://badges.gitter.im/TextClassifier2/TextClassifier2.svg)](https://gitter.im/TextClassifier2/Lobby?source=orgpage) 

Advanced neural network based on https://github.com/RusZ/TextClassifier

TODO
- Mention about DDD paradygme in description
- Transfer network learning to Apache Ignite platform or S3 Cloud 

Text Classifier

Application for text classification using neural networks.
Requirements

    Java SE Development Kit 8 (jdk-1.8)

Dependencies

    Encog Machine Learning Framework (org.encog:encog-core:3.3.0)
    Apache POI (org.apache.poi:poi-ooxml:3.16)
    SQLiteJDBC (org.xerial:sqlite-jdbc:3.19.3)
    JUnit (junit:junit:4.12)
    H2 Database Engine (com.h2database:h2:1.4.196)
    Mockito (org.mockito:mockito-core:2.8.47)
    Hibernate ORM (org.hibernate:hibernate-core:5.2.10.Final, org.hibernate:hibernate-entitymanager:5.2.10.Final)
    SLF4J (org.slf4j:slf4j-log4j12:1.7.25)
    Javassist (org.javassist:javassist:3.22.0-CR2)

Config.ini file description
Parameter 	Description 	Possible values
db_path 	Path for database files and trained classifiers 	Example: ./db
dao_type 	Method of data storage and access 	jdbc, hibernate
dbms_type 	Database management system 	sqlite, h2
db_filename 	Database name 	Example: TextClassifier
ngram_strategy 	Text splitting algorithm 	unigram, filtered_unigram, bigram, filtered_bigram
Quick start guide

    When you launch application first time, it will ask you for XLSX-file with data for training. The file can include one or two sheets. First sheet should contain data for training, second sheet should contain data for testing of accurancy. File structure:

    After that application will build vocabulary, will create and train neural network for each Characteristic.
    Restart application and use it for text classification.

Author

    Ruslan Zakaryaev

License

This project is licensed under the MIT License - see the LICENSE file for details.
