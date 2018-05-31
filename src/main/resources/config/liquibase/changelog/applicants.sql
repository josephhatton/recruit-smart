--liquibase formatted sql


--changeset applicant:name runOnChange:1
insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Brian', 'Taylor', 'btaylor0@columbia.edu', '1-(130)502-4599', 'Recruiting Manager', 0, '2016-03-29 14:02', 'Professional Summary:
Programmer Analyst with 5 years of experience with over 2 years of exclusive software development experience, a strong educational background developing full lifecycle core Java applications as well as corporate technical support and development experience. Able to integrate and adapt to new information, is organized and can work independently or in a team environment. Has ability to quickly analyze and resolve technical problems. Possesses excellent interpersonal communication skills, a positive attitude and incredible aptitude, teamwork skills, technical troubleshooting skills, and maintains a strong technical understanding.

Technical Summary:
Programming: Java, Java EE, JavaScipt,Ext JS, C/C++, X86 Assembly
Databases: Oracle SQL, MySQL
Scripting: Bash Scripts, VB Scripts
Web Development: HTML,DHTML, XML, CSS, JSP, ASP, Apache, JBoss
Frameworks: Spring, Struts2, Hibernate, REST, SOAP
Development Tools: Eclipse, IntelliJ Idea, DB Visualizer, Ant, Maven, SVN, JUnit, LOG4j
Platforms: Microsoft Windows, Mac OS X, Unix, Linux
SDLC: Requirements Gathering, Documentation, Development, QA/Testing, Implementation, Maintenance, Waterfall, Agile
Methodologies: Object Oriented Programming and Design, MVC architecture

Professional Experience:
NISC 5/11 – Present
Software Specialist
Research, design, and develop applications for the Java based iVue enterprise system. Work collaboratively with analysts and quality assurance to drive application development through the software development lifecycle successfully. Primarily assigned to projects on the COBOL to Java project, rewriting and enhancing legacy COBOL applications to Java.
•          Participate in all phases of the systems life cycle. Design, documentation, code and test applications.
•          Responsible for managing trouble ticket queue, timely resolution of all tickets regarding application issues, resolving issues related to data integrity, break/fix issues with code. Involved in the investigation, analysis, and resolution of current application problems.
•          Involved in the investigation, analysis, and resolution of current application problems.
•          New features development based on change requests approval and peer code review processes.
•          Researched the viability of implementing hibernate to avoid the need of writing Oracle and Postgre versions of applications. It was determined that the costs of training the programmers in Hiberante was not justified for the small number of applications requiring Postgre SQL.
Technical Environment: Java, JDBC, XML, Oracle SQL , Linux, Bash Scripting, Tortoise SVN

MasterCard Worldwide 3/11 - 5/11
Software Developer (Contractor)
Lead the frontend development team, consisting of 6 developers on 2 continents, for the internal Billpay application.
•          Designed and developed the presentation layer solely using Ext JS employing the MVC architecture to the front-end
•          Used JSON objects delivered through RESTful services for client-server interaction
•          Met weekly with the client to present progress and gather more requirements.
•          Completed the front end ahead of schedule and shifted our team to help with the back end.
Technical Environment: Java, Hibernate, SON, Ext JS, JavaScript, Oracle SQL, JBoss, Tortoise SVN


Prospect Infosys 11/10 - 3/11
Software Developer Trainee
Completed 5 months of classroom and workshop training to prepare Java EE consultants. Training was conducted through 8 hours of weekly classrooms training and labs to be completed on our own.
•          Setup a personal development environment installing and configuring Eclipse, Java EE, Apache, MySql, Hibernate, Struts2, and Spring.
•          Training focused on practical enterprise level application development and covered topics beginning with Core Java, web development, Java EE, Struts2, Hibernate, Agile Development, and Design Patterns.
•          Completed a running project that built a student database and interface using each technology that was being studied. Each iteration of the application was based on MVC principle. Beginning with Java, JDBC, and MySQL using the console as an interface, the project evolved into a web interface and hibernate using Servlets, Struts2, and ending with Spring.
Technical Environment: Java, Java EE, JDBC, MySql, Apache, HTML, CSS, JavaScript, Servlets, JSP, Ext JS, Hibernate, Struts2, MySql, Spring, Ant, JUnit, Log4J, Tortoise SVN

Monsanto 1/08 – 06/10
ARC Team System Analyst
Selected to work in the Analysis, Reporting, and Consolidation team to administrate, support, configure, and maintain Hyperion Essbase and Planning systems. Developed internal tools to simplify daily tasks using Java.
•          Improved existing backup procedures and automated time consuming manual procedures.
•          Generated Sales Tax Reports using shell scripting. Wrote Bash Shell Scripts to automatically iterate through each city and county by reading from a file that fed the script the id number of each location. The script would also need to be aware of what time frame you are reporting for and adjust it self for leap years and the different lengths of each month. All of the reporting was neatly formatted into a text file that would print and was exported to an Access database for archiving.
•          Created a tool to copy load rules and load the database with data from the SQL Server. Used Java to develop an application that would create two objects – one connected with the source and the other the target. The users could then select the load rules file to copy and the option to load data from SQL Server.
•          Created and used SQL queries using SQL Navigator to figure out the effective access of Essbase and Planning users.
•          Reduced system recovery time by 80% by designing and implementing a hot backup protocol for Hyperion Essbase servers.
•          Managed global project successfully automated 95% of the deployment of Hyperion Smartview 11 to end-users.
Technical Environment: Java, Essbase Java API, VB Script, and MAXL, UNIX, Bash Scripting, SQL Navigator, SQL.

Educational Experience:
Washington University in St. Louis	Maryville University
Bachelor of Science in Computer Science	Bachelor of Science in Mathematics
Graduated Fall 2007	Graduated Fall 2007
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Phillip', 'Edwards', 'pedwards1@vk.com', '262-(790)551-4949', 'Research Nurse', 0, '2016-03-29 14:02', 'Professional Profile:

•          11 years experience with Java EE-J2EE based systems in full SDLC development.

•          Over 3 years Web Services experience with JAX-WS and JAX-RPC.

•          Over 3 years experience developing Web 2.0 applications using GWT, jQuery, and YUI.

•          Using Hibernate since 7/2009 and the Spring Framework since 11/2010 .

•          15 years overall programming experience, including 1.5 years in a Technical Architect role and 6 months in a Lead Developer role.

•          Experienced in Agile, SCRUM, TDD, RUP, UX, and UML methodologies.

Career Objective:
To build and deliver quality software products and improve existing applications for the business community, utilizing the most effective technologies and methodologies based on the needs of the client.

Achievements:
•          Successfully removed the pdf generation code from a 32mb legacy code base, creating a standalone application in order to upgrade all legal documents to a newer version of MS Office, which was mandated by state government regulations at Safety National.
•          Became proficient with the Spring Framework through self-study, which brought me into the position for US Bank.
•          Solved an Eclipse perm gen error that was posted on the companies technical forum and discovered a JavaScript bug with their framework at Edward Jones.
•          Identified and fixed a long standing concurrency bug that had been distorting reports at CSC.
•          Responsible for reducing the applications high transaction error rates from 13.5% to less than 1% at AT&T.
Technical Profile:

Languages: Java 1.2-1.7, SQL, XML, JavaScript, HTML/DHTML, CSS, Perl 5, Korn, Bash, and Bourne Unix Shells, Visual Basic 4-6, , VBScript, VBA.

Frameworks Spring 2.x-3.x Frameworks, Spring MVC, Spring JDBC, Spring JMS, Spring AOP, Spring Transactions, , Apache CXF 2.2.4, Hibernate 3x, iBatis, GWT 1.4-2.4, GIN, GUICE, STRUTS 1x , STRUTS2, Tiles, JPA2, SAML, log4j, JUNIT 3x-4x, Castor 1x, PowerShell, Edward Jones 2.x Framework.

Technologies: Java EE/J2EE, EJB 2.0, Servlets, JSP, Web Services, SOA, REST, AJAX, JSON, Web 2.0, RIA, XSL/XSLT.

Libraries & APIs: JAX-WS, JAX-RPC, XHR, SAX2, SAX2, JDBC, JMS, ESAPI, JDO, POI, slf4j, Regular Expressions, Javamail. Custom Tags, JSTL, Xdoclet, jClass, jFreeChart, iText, EasyMock 2.4, Jasper Reports.

Methodologies: Agile, SCRUM, OOAD, SDLC, RUP, TDD, UML, UX (User Experience), and Waterfall Model

Application Servers: JBoss 4.3, BEA Weblogic 8x-11x, Tomcat 5-7, Glassfish 9.1-3.0.1, Sybase EA Server 4.2-5.2,
Websphere 6.1

Database: Oracle 8i-11g, Sybase 12.5.1, DB2, MySQL 4-5, SQL Server 6.5 & 2005, Derby 10.x

Java IDEs: Eclipse 3x, STS 2.6x (SpringSource Tool Suite), RAD 6x-7x, WSAD 4x-5x

Tools: Maven, ANT, Hudson, Artifactory, SoapUI, TOAD, MKS Integrity, RPM , viEditor, Cygwin, PuTTY. JIRA, Confulence, Crucible, Mercury Test Director.

Source Control: CVS, Subversion, Changeman, ClearCase, SCCS, VSS.

Operating Systems: Windows (all), UNIX, Linux.
Experience:
Sr. Java Developer September 2012 to December 2012
Suddenlink Communications
St. Louis, MO
Modifications and enhancements for a CRM (Customer Relations Management) application.

Environment:
Java 1.6 JavaEE, Servlets, JSP, jQuery 1.8.2, Web Services, Spring 3.1.0, Spring MVC, Apache CXF 2.2.4, SoapUI, JSTL, Hudson, Artifcatory, JUNIT, HTML, CSS, Maven, Tomcat 6, TOAD, Oracle 11g.

Sr. Java Developer April 2012 to July 2012
Enterprise Leasing
St. Louis, MO
New development end to end work using the Spring Framework, Hibernate, jQuery, Struts2 and Web Services working with Tax applications.

Environment:
Java 1.5, JavaEE, Servlets, JSP, jQuery 1.6.4, Struts2, Spring 3.0.5, Spring AOP, Spring Transactions, Spring DAO, Spring JMS, SOA, Hibernate 3.2..2, JavaScript, Agile, JUNIT, Easymock, HTML, CSS, ANT, Weblogic 11g, Oracle 11.

Sr. Java Developer October 2011 to April 2012
CDG/Division of Boeing
Hazelwood, MO 63042
Doing prototyping work, creating utility programs and scripts, requirements gathering, and technology research for interoperable software with COTS (Custom of the shelf software) products for S1000D technical documents.

Environment:
Java 1.6, JavaEE, Servlets, JSP, GWT 2.4, JAX-WS, JavaScript, JUNIT, HTML, CSS, Korn shell scripts, SED, Perl, PowerShell, VBScript, UX, CVS, Maven.

Sr. Java Developer July 2011 to October 2011
Safety National
St. Louis, MO 63146
Worked on the migration team that worked on both developing new applications and modifying existing
applications for newer technologies.

Environment:
Java 1.6, JavaEE, ECS, JSP, Servlets, Spring 3.0, Spring Batch, AOP, SOA, Hibernate 3.5, jQuery, DB2, JPA2, Agile, HTML, CSS, JUNIT 4x, Maven 2.2.1, CVS, AS400, MULE ESB, Perl 5, iText, XML, Xpath, vbscript, WSH (Windows Script Host), JDBC, Hudson, STS, Nexus, Websphere 7x.

Java Developer March 2011 to June 2011
MasterCard
OFallon, MO 63368
New intranet web site using Ajax with GWT that exposes apis as consumable web services.

Environment:
Java 1.6, JavaEE, Spring 3.0, GWT, GIN, GUICE, Hibernate 3.5, Oracle 11g, SOAP, REST for Oauth, JPA2, SCRUM, EasyMock, slf4j, HTML, CSS, Maven, TOAD, JUNIT 4.7, Subversion.

Websphere Developer November 2010 to February 2011
US Bank
St. Louis, MO 63101
New development work for an accounting, reporting, and data warehousing platform that monitors loan data.

Environment:
Java 1.6, JavaEE, Spring Framework 2.5, Spring MVC, Spring Security, Spring JDBC, Servlets, JSP, SQL Server 2005, JavaScript, Tiles, JSTL, Custom Tags, YUI (Yahoo User Interface), JUNIT, HTML, CSS, RAD 7.5, Websphere App Server 6.1, ClearCase.

Web Application Security:
OWASP ESAPI APIs, Oracle Access Manager (OAM), Idenitity Management-COREid, WebGate, eDirectory (LDAP).

Sr. Software Engineer February 2010 to November 2010
Edward Jones
Maryland Heights, MO 63044
Enhancement work to develop a new financial product for daily trading.

•          Working as the sole java developer on the project, completed the work one month ahead of schedule.
•          Responsible for MKS Integrity deployments for teams working with the brokerage systems.
•          Worked on modifications to existing Bourne shell scripts.
•          Called in as an SME for another team to design and develop code for pdf documents using iText.

Environment:
Java 1.4, J2EE, Servlets, JSP, EJB 2.0, SQL, JavaScript, XDoclet, STRUTS, Tiles, JSTL, Custom Tags, Castor 1.0, jClass, ANT, JDBC, JUNIT, HTML, CSS, BEA Weblogic 8.1-10.3, Bourne shell scripts, CTG, CVS, UNIX, Linux, RPM, Stellant, MKS Integrity, Maven.
Sr. Software Engineer July 2009 to January 2010
IronData Software
Chesterfield, MO 63017
New end to end Web 2.0 (RIA) software development for a prototype workflow software product to be sold to
government agencies under Section 508 standards.

•          Led the technical team in St. Louis and mentored other developers.
•          Researched, submitted, and implemented architecture proposals for the application.
•          Designed and developed “drag n drop” for the application.

Environment:
Java 1.6, Java EE, GWT 1.5-1.6, Hibernate 3.2-3.3, Tomcat 5.5, mySQL 5.1, Derby 10.1, SOA, RUP,POJOS, UML 2.0, TDD, Hudson Server, slf4j , JUNIT, and Subversion 1.6.

Sr. Software Engineer November 2008 to December 2008
Edward Jones
Maryland Heights, MO 63044
New end to end J2EE development for a financial advisor supervision application.
•          Using Agile techniques, completed work 3 weeks ahead of schedule.

Environment:
Java 1.4, J2EE, Servlets, JSP, EJB 2.0, SQL, JavaScript, XDoclet, STRUTS, Tiles, JSTL, JSON, SOAP 1.1, SAML, ANT, JDBC, JUNIT, HTML, CSS, LDAP, AJAX widgets, BEA Weblogic 8.1, BEA Tuxedo, DB2, CVS, UNIX

Sr. Software Engineer January 2008 to November 2008
Edward Jones
Maryland Heights, MO 63044
New end to end J2EE development for a fee based mutual fund program utilizing Web Services with SOAP for B2B management of financial Asset Models for customer stock portfolios.

•          Solved a PermGen error with Eclipse and was requested to post the solution to the Edward Jones developer forum.
•          Discovered an existing production JavaScript bug in the company’s framework account header window.
•          Introduced a company sortable table widget with JSON objects to the application for the display and retrieval
of PDF documents.
•          Coordinated and resolved performance test configuration issues with Load Runner and reduced the time to 1/3 of the previous release.

Environment:
Java 1.4, J2EE, Servlets, JSP, EJB 2.0, SQL, JavaScript, XDoclet, STRUTS, Tiles, JSTL, Custom Tags, JSON, SOAP 1.1, SAML, ANT, JDBC, JUNIT, HTML, CSS, LDAP, AJAX widgets, BEA Weblogic 8.1, Oracle 9i, CVS, UNIX

Sr. Software Engineer November 2006 to January 2008
Computer Sciences Corporation (CSC)
O’Fallon, IL 62269
New and enhancing J2EE Java development for GATES (Global Air Transportation Execution System) which is utilized by the AMC (Air Mobility Command) and USTRANSCOM (United States Transportation Command) for the Department of Defense.

•          Replaced a technical architect and stepped into that role as a team representative to the architecture group for standards, code reviews, and problems resolution for group team issues
•          Refactored and redesigned the application to allow for a new thread safe Singleton pattern for a dynamic report threading model to co-exist with the existing static cache.
•          Identified and fixed a long standing random threading issue by utilizing the Initalize-On-Demand Holder Class idiom.
•          Designed and developed a new adhoc report generation module to allow users to dynamically build sql queries.
•          Designed and developed a refactoring plan for revising the existing RBAC (Roles Based Access Control) group security roles of the application to incorporate an additional location level security role.

Environment:
Java 1.4-1.5, J2EE, Servlets, JSP, SQL, XML, JavaScript, iBatis, JMS (EA Server Message Builder) ANT, STRUTS, Tiles, JSTL, SAX2, XHR, JDBC, POJOs, jFreeChart, iText, log4j, HTML, CSS, DHTML, CSS, EAServer 4.2-5.2, Sybase 12.5.1, eChangeman, UNIX

Developer Roles at AT&T Overall dates: March 1999 to November 2006
Sr. Analyst August 2004 to November 2006
AT&T
Kirkwood, MO 63122
New and enhancing Java development for an online web shopping cart application with high transactions volume: 10,00+ daily for all online AT&T products.

•          Designed and developed a custom “browser back button” page tracking mechanism using a stack to handle multiple exit options from the main DSL ordering page.
•          Completely refactored and redesigned all the metrics code and became the SME (subject matter expert) for metrics.
•          Received a “Wall of Fame” nomination by peers for work above and beyond expectations on 6-30-2005.

Environment:
Java 1.4-1.5, J2EE, Servlets, JSPs, XSL, XML, JAXB, STRUTS, JavaScript, JDO, JDBC, UX, ANT, log4j, IBM Websphere 4.0-5.2, RAD 6.0, Oracle 9i, CVS, UNIX

Sr. Analyst May 2003 to August 2004
AT&T
St. Louis, MO 63101
Developed new applications for the Billing Services department working with customer projects relating to CRM and DSL.

•          Created an Opt-Out program with another developer that pioneered STRUTS in our department. The website was announced in an email to the entire company.
•          Created an online router disconnect utility application that used STRUTS and SOAP to connect to an EJB acting as a Web Service.
•          Designed and developed a customer query EJB for getting customer information.

Environment:
Java 1.3,J2EE, Servlets, JSPs, STRUTS, EJB 2.0, JMS, SOAP, JDBC, ANT, log4j, IBM Websphere 4.0, Oracle 9i . CVS, UNIX

Sr. Analyst October 2001 to May 2003
AT&T
St. Louis, MO 63101
Continued new development with the eRepair website, but was promoted to Sr. Analyst.

•          Successfully proposed, introduced, and implemented Excel file attachments with JavaMail for batch metrics reports using the POI api in Java. It then became the reporting standard for the department.
•          Correctly identified and resolved two problems with our metrics reporting after a release, due to a robot hitting our site and a concurrency bug.

Environment:
Java 1.2, J2EE, Servlets, JSPs, POI, JavaMail, batch programs, JDBC, ANT, log4j, IBM Websphere 3.xx, Oracle 8i, SCCS, eChangeman, Unix Shell Scripts, UNIX

Analyst February 2001 to October 2001
AT&T
St. Louis, MO 63101
Developed Internet web applications for an eRepair website with 4,000+ hits per day. This pioneer website was designed to alleviate heavy Call Center traffic and allow customers online access to trouble ticket information.

Environment:
Java 1.2, J2EE, Servlets, JSPs, POI, JavaMail, Batch programs, JDBC, ANT, log4j, IBM Websphere 3.xx, Oracle 8i, SCCS, eChangeman, Unix Shell Scripts, UNIX

Analyst March 1999 to February 2001
AT&T
St Louis, MO 63101
Designed and developed programs that supported Middleware leasing software applications. These highly visible applications were used for life-cycle tracking for all employees leased pcs, over 240,000.

•          Developed an original VBA Report Generator program to create Excel reports each week and then send them using Outlook to the recipients to automate a series of reports, mechanizing a previous manual process.
•          Identified and fixed a long standing memory leak problem with the ODBC driver that previously had been poorly coded with a looping workaround.
•          Had extensive VBA experience and became the VBA Excel expert for the team.
•          Created new stored procedures, tables, reports and maintained existing ones.
•          Responsible for enhancing , maintaining and troubleshooting .DLLs with our applications.
•          Mentored & trained developers during the transition process to close out the project for maintenance only work by
an Offshore team.

Environment:
Visual Basic 4.0-6.0, SQL Stored Procedures, VBA (Word, Excel, Outlook, Access), DOS batch programs, Crystal Reports, SQL Server 6.5, InstallShield, VSS, NT

Owner: Programmer/Analyst September 1997 to March 1999
AyudaComp
Fenton, MO 63026
Part-time consulting work with small businesses that transitioned into full time programming, over an 18 month period, by creating and implementing a successful business plan, while still employed full-time in another field.

•         Wrote a class module program using the Windows API in VB 5 as a copy protection scheme for programs.
•         Provided software and PC training for clients.
•         Designed a data entry program for a small business using Visual Basic 5 and carried out full life cycle application development utilizing an Access database linked to unbound controls.

Environment:
Visual Basic 5.0, Microsoft Access, PDW, Windows

Education:

BA University of Missouri at St. Louis
St. Louis Community College

Government Clearances:

Security Clearance. Approved on 8-16-2009.
NACLC Approved on 3-21-2007.
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Johnny', 'Austin', 'jaustin2@spiegel.de', '63-(155)460-7959', 'Structural Analysis Engineer', 0, '2016-03-29 14:02', 'Senior DB2 / SQLServer Database Administrator | SQL Programmer/Developer

EXECUTIVE SUMMARY
A self-motivated, goal-oriented senior level SQL DEVELOPER & SQL/DB2 DBA with demonstrated records of challenging achievements with over 20 years of progressive IT experience in relational database administration, design, and application support. An advanced database administrator, having extensive experience in analysis, physical and logical database design, installation, configuration, maintenance, performance monitoring, tuning, query analysis, ETL, backup/recovery, stored procedures, SQL, and Q-replication. Mid-range: (over 15 years) AS/400, IBM S/3X, OpenVMS VAX, AS400-DB2, DB2 SQL/400, COBOL, COBOL ILE, Query Manager/400, File Scope, Client Access 400-ODBC, UNIX/AIX.

Extensive involvement in Cluster Environments (HACMP, HADR, Veritass VVR, GRIDSCALE), Data Warehouse, Fault Tolerance, Security and Disaster recovery planning. Experience in Data Modeling tools (Erwin, ER Studio, and Rational Data Modeler). Work extensively with ETL tools like Data stage, Informatica and ETL Extract using VLDB environments.

PROFESSIONAL EXPERIENCE

Newspaper Data Exchange (NDX)
Chesterfield, MO  Industry: Marketing   03/2013 to October 2014
Database Architect
Responsibilities include all phases of SQL system, ETL, database design, application support.
•         Planned and Migrated Database Conversion from SQL Server 2008 to 2012.
•         Designed and developed database tables in SQL Server 2008/ 2012 for new modules in application.
•         Collaborate directly with CEO/President, software developers and business analyst to understand business or industry requirements.
•         Design databases to support business applications, ensuring system scalability, security, performance and reliability.
•         Develop data models for applications, metadata tables, views and related database structures.
•         Created numerous simple to complex queries involving self joins, correlated sub-queries, functions, cursors, dynamic T-SQL.
•         Written Stored Procedures to display the data in front end application.
•         Utilized T-SQL on a daily basis in creating customs view for data and business analysis.
•         Tuned and optimized queries by altering database design, analyzing different query options, and indexing strategies.
•         Optimize existing T-SQL code.
•         Analyzed Execution Plans to troubleshoot potential performance issues.
•         Utilize Dynamic T-SQL within functions, stored procedures, views, and tables.
•         Customized script utilizing expressions and VB scripting to enhance tasks in SSIS.
•         Created and maintained SSIS packages designed for replication of data.
•         Used the SQL Server Profiler tool to monitor the performance of SQL Server – particularly to analyze the performance of the stored procedures.
•         Design high availability solutions and implemented database mirroring and replication for the data security.
•         Evaluated the physical model and employed best practices in creating a complete schema including tables, relationships, stored procedures, views, clustered and non-clustered indexes and triggers.
•         Assist in Unit Testing on different scopes of Data Architecture.
RGA:  Chesterfield, MO  Industry: Insurance   03/2011 to 03/2014
Senior DB2 Database Administrator / Onsite & Remote Position
Responsibilities include all phases of DB2 system and application support. Currently supporting this former client remotely on an “as needed contingent basis. Responsible for all DB2 installs, upgrades, and after-hour technical support needs. I support multiple client environments and work with remote overseas clients to quickly resolve all critical DB2 issues. I also provide assistance with load capacity testing, documenting technical requirements and providing architecture blueprints for future DB2 environments.
• Provided primary administrative and Architect support for DB2 UDB V7, v8 and V9 databases in Linux and Windows and AIX P-SERIES, OLAP environment
• Managed and resolved design issues during development stage and worked closely with developers
• Performed database backups using TSM for storage management
• Used Agile ECM (Enterprise Content Management) tool to deliver solutions rapidly to solve complex problems
• Moved database Schema changes from development thru production.
• Performed database tuning using Quest monitoring tool and custom DDL scripts
• Performed Database Replication on LDAP servers using HADR
• Performed Database Replication on Linux Servers
• Created databases and database objects
• Modified and enhanced existing databases
• Performed backups recoveries and reorganizations of databases
• Replicated mainframe databases to new mainframe databases and AIX P-SERIES
• Replicated database updates using Replication Center
• Created Data Warehouse sources and processes using Data Warehouse Center
• Created and setup database trigger audit procedures• Created database reports
• Configured ODBC connection to databases for use by Microsoft Access (EE only)
• Performed database migrations
• Provided ongoing database support including performance enhancement maintenance and end-user education
• Performed DB2 installation and configurations


Nestle Purina:  St. Louis, MO   Industry: Manufacturing 08/2010 - 02/2011
Senior DB2 Database Administrator / Remote Position
Responsibilities include all phases of DB2 system and application support. Acting as the sole DB2 project DBA, I was responsible for overseeing the software upgrades for all of Purinas Manufacturing plants in US and Canada. In addition, I was responsible for 24/7 on call support, managing complex data propagation/replication scenarios from various database sources (Oracle, SQL Server, Mainframe), troubleshooting, performance tuning and working with complex UDFs/ Stored Procedures, and handling complex integration issues. Modified and enhanced existing databases

• Performed backups recoveries and reorganizations of databases
• Responsible for all ETL
• Replicated database updates using Replication Center
• Created Data Warehouse sources and processes using Data Warehouse Center
• Created database reports
• Performed database installs, upgrades ,fix packs and migrations
• Provided ongoing database support including performance enhancement maintenance and end-user education

Peekabuck Media:  St. Louis, MO   Industry: Advertising/Marketing  07/2008 - 04/2010
Manager of Database Support & Operation
Responsibilities include all phases of DB2 system and application support. In addition, I supported multiple DB2 DPF environments, managed multiple standalone Oracle 10g/Linux databases used for data replication, provided support on software upgrades, technical problems, and software solutions.

United Healthcare: St. Louis, MO  Industry: Health Insurance   10/2007 - 06/2008
Senior DB2 DB2 Database Administrator
Supported multiple environments: (8) partition, (4) node DB2 Data Warehouse on AIX version 5.3; the environment involves working closely with Web-sphere 6.1, Query Patroller, AlphaBlox, Data Miner, and Report Net. Unix & Mainframe environment. Responsibilities include all phases of DB2 DBA support including. DB2 9.5 upgrades, upgrading Tivoli Storage Manager from v5.2 to v5.4, creating load scripts, scheduling loads, and working closely with the ETL team. Also implemented MDC and summary tables and configured the use of cube views, and decommissioned obsolete DB2 Servers.

Lockheed Martin: Baltimore, MD  Industry: Medicare/Medicaid   02/2007 - 10/2007
Senior DB2 Database Administrator (Government Security Clearance)
Responsibilities include all phases of DB2 system and application support including the managing the recover ability of Unix and Solaris mid-tier DB2 databases in a Windows, Unix, and Mainframe environment; mid-range systems. I was primarily responsible for designing and implementing disaster recovery techniques and documenting High Availability and Disaster Recovery (HADR) options. I worked alongside MQBroker and Q-Replication team members in their DB2 efforts.


StateFarm: Bloomington, IL  Industry: Finance & Insurance   02/2006 - 02/2007
Senior DB2 Database Administrator
Responsibilities include all phases of DB2 system and application support including configuring and supporting complex Q-Replication and DB2 Data Propagation scenarios that spanned over 30 mainframes in a zSeries Parallel Sysplex data sharing environment, and hundreds of clustered AIX / Solaris and Unix federated Servers; mid-range systems.

A6 Investments:  Chicago, IL  Industry: Finance & Real Estate  07/2002 - 02/2006
Senior DB2 Database Administrator
Responsibilities include all phases of DB2 system and application support. I also assisted developers using Micro-Focus COBOL Net Express Compiler, to quickly build legacy and client server environment PeopleSoft application programs in windows/Unix environment. LUW environment. Other daily task included providing direct internal and external support on software solutions and technical problems, working closely with UNIX system administrators, implementing cross platform backup and recovery strategies, and coordinating with other mid-tier DBAs and project teams to implement cluster infrastructure enhancements for large partitioned databases.

Hitplay Media: Los Angeles, CA  Industry: Advertising/Marketing    07/2000 - 06/2002
Senior DB2 Database Administrator / Remote Position
Working as a Senior UDB DB2 DBA my primary responsibility was the implementation of SQL based replication on UNIX AIX servers. I also provided extensive logical and physical database design, managed database security, wrote stored procedures, updated COBOL run-time by re-linking it to include the new routines for ACUCOBOL-GT, created triggers, modified existing KORN scripts, setup and administered test environments, and scheduled and verified all nightly backups. To support calling shared libraries on the UNIX systems, I converted all objects and executable into ELF format and ensured the ACUCOBOL-GT run-time was is an ELF executable in order to use shared libraries. In addition, I assisted with a full DB2 version upgrade from v6 to v7.1, applied critical fix packs, and performed performance analysis for distributed client server applications.

Optimos / Cushman & Wakefield: NY, NY. Industry: Real Estate.  08/1998- 06/2000
Technical PeopleSoft DB2 Database Administrator
Working as a PeopleSoft Technical Lead DB2 DBA, I assisted with several PeopleSoft Financial implementations (Kemper Insurance, Cushman & Wakefield, Atlantic Duncan, Gateway Systems) on AS400 and OS-390 Mainframes; supporting functions teams with up to 10-15 members. Beyond preparing the database server and installing PeopleSoft software, my scope of work included process scheduler configuration, tools and service pack upgrades, migrating projects using application designer, reviewing compare reports, troubleshooting database and system issues, and working with software and hardware vendors. I also setup PeopleSoft security, administered database users, permissions and database security. Ensuring data recover-ability, proactive management of database and file system growth, and proactive checks to ensure database and system health, was a daily routine; including providing support for customer issues through the use of a web-based ticketing system. As the PeopleSoft Technical DBA, I also actively participated in management meetings, and offered management advice on applications implementations, upgrades and general maintenance.


Econometrics:  Chicago, IL Industry: Advertising & Marketing.   11/1997 – 08/1998
Senior DB2 Database Administrator
Inside a small but growing web-based marketing firm (a 6 tera-byte database), I worked as a solo DB2 DBA, in a UDB EE environment, supporting 9 application developers. This dynamic UNIX environment, required “on the fly” changes and implementations. Some changes going straight into production, with very little room for error recover-ability. Projects required a tremendous amount of logical and physical database design and heavy SQL tuning and analysis. I applied critical DB2 fix packs, upgraded DB2 clients, spear headed Y2K data conversion efforts, and assured the archival and recover-ability of production data from/to tape backups devices.

IBM:  Baltimore, MD  Industry: Government Human Resources. 11/1996 – 11/1997
Senior DB2 Database Administrator (Government Security Clearance)
I was hired as a Senior DB2 DBA to assist the Maryland State Police alongside a small team of IBM consultants who were designing a new law enforcement reporting application. I forward engineered the team’s logical designs into physical databases and also managed database security and assisted with backup and recovery in a Mainframe environment.

American Management Systems, Lakewood, CO.  Industry:  Software 07/1996 – 11/1996
Senior DB2 Database Administrator
Functioning as team lead, I worked hands-on with my DBA group to design and implemented new stored procedures to upgrade statistics based on index strategies, assisted with the development of new processes and procedures to update the test environments, as well as proposed and implemented table fragmentation strategies that resolved data availability issues. This project involved creating DB2 stored procedures and managing DB2 databases that supported new billing methods for a new Swedish Mobile Phone System. Numerous small tasks were assigned to our DBA group. Major tasks involved developing custom generic data loaders to handle data sent by new wireless clients and testing automatic restart in case of failure, and error logging on an Open VMS VAX System.

Compusoft : Baltimore MD Industry: Software Development 09/1995 – 06/1996
Senior DB2 Database Administrator
Working as a PeopleSoft technical DBA, I provided database support for a post PeopleSoft Financial implementation. Tuning efforts reduced PeopleSoft batch jobs from 1 hour to 10 minutes. I assisted with new project and data migrations using Data Mover, configured Process Scheduler, and applied PeopleSoft patches and DB2 Fix packs.

DuPont :  Wilmington DE   Industry: Manufacturing  01/1995 – 09/1995
DB2 Database Administrator
I utilized Micro-focus COBOL to and TELON to support IMS to DB2 conversion efforts. I also assisted in data migrations and tape backups by unloading large dynamic tables, and restoring small static tables from tape. Also scheduled and verified data refreshes and backups.


MCI:  Crystal City VA   Industry: Telecommunications 09/1994 – 12/1994
DB2 Database Administrator
This was a very large and dynamic environment with many simultaneous DB2 projects. As a DB2 DBA my scope of work was scattered across the board: from modifying KORN scripts for data extraction to calculating projected table size and database growth, assuring successful backups, working closely with the project System Administrator in implementing hardware changes, and accessing hardware requirements for new test servers. I also acted as a team lead participant. Purging and archived data out of large critical tables by using existing scripts and trying to improve performance and data availability, and using parallel scans to retrieve data were also routine tasks.

',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Kenneth', 'Riley', 'kriley3@webmd.com', '47-(107)899-4538', 'Sales Representative', 0, '2016-03-29 14:02', 'OBJECTIVE
To leverage my business education and technical experience to work with talented teams to deliver innovative solutions that solve real world problems. I have very broad and deep technical skills, excellent writing and communications skills, and work very well alone or in team environments. I spend a considerable amount of time keeping abreast of technology and trends, and keeping my hands-on technical skills up to date. I focus on results, prefer a dynamic, fast-paced environment, and raising the bar. I believe there is always room for improvement, be it in design or architecture, development methodology, quality assurance, marketing, delivery and support, and ultimately, customer satisfaction.

EXPERIENCE
April 2012-Present AMS Controls – Maryland Heights, MO – Senior Software Engineer
AMS Controls develops and manufactures hardware controllers and software for the metal roll forming industry and has thousands of customers globally. Primary responsibilities include architecture, design, development, testing, deployment and support of new business applications. These applications provide features such as executive dashboards, interactive reporting, order entry, scheduling, inventory management, and alerting (messaging to mobile phone via text, email alerts, etc.) to improve productivity and throughput, “mistake proof” orders, and reduce downtime by providing greater visibility into operations in real-time.


Jan 2011-Sep 2011 Oracle – Redwood Shores, CA – Principal Member of Technical Staff (Telecommuter)
Oracle acquired Passlogix as of Jan. 1, 2011 – continued duties on eSSO architecture, as part of the Identity Management group, Fusion Middleware. (Telecommuting position)

May 2008-Dec 2010 Passlogix – New York, NY – Senior Engineer/R&D/Enterprise Architect (Telecommuter)',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Timothy', 'Arnold', 'tarnold5@dot.gov', '86-(454)859-8917', 'Operator', 0, '2016-03-29 14:02', 'EXPERIENCE
Key Technologies: C#, Silverlight/WPF, ASP/MVC, Web Services/REST/WCF, EntityFramework, SQL Server, Telerik Reporting, SignalR, Reactive Steams, NServiceBus
Passlogix was the industry leader in single sign on (SSO) technology for the enterprise. As a member of the engineering team I worked on a variety of enhancements to various components of the platform and developed the architecture and implementation of the reporting sub-system, a key missing feature.
MONSANTO, St. Louis, MO (9/2012 – present)
Consultant
System integration of automated research test and analysis system used in the R&D of generic research in lab and in field. Reduced yield analysis time by 50% via application running a measurement system built from PLC and interfacing with external testing apparatus. Created software to control next generation research planter. Control and monitor entire system via PLC program running (CoDeSys) interfacing with C# application recording seed placement with GPS accuracy to support generic tracking AT&T, St. Louis, MO (11/2010 – 9/2012)
Consultant
Work on AT&Ts Billing infrastructure component managing all customer remittance - duties include the following:
•          Develop JAVA EJB methods to allow open secure payments to/from AT&T via internet, kiosk and 3rd party vendors.
•          Create JUNIT tests for all new code and support test team in development of tests for legacy code.
•          Develop C applications on IBM/AIX with optimized SQL transactions, JAVA calls and time critical cross-platform messaging.
•          Develop requirements based testing suites to validate any new or modified functionality.
•          Maintain healthy coordination with QC department to ensure quick test turnaround and issue resolution.
•          Intense coordination with business partners (e.g. JPM Chase) to ensure secure transactions under tight deadlines.

TECHGUARD SECURITY, Chesterfield, MO (11/2010 – 11/2010)
Consultant
•          Developed video compression filter plug-in using C/C++ within embedded systems.

DRS TECHNOLOGIES, St. Louis, MO (11/2007 to 11/2010)
Senior Software Programmer
•          Migrated MFC C/C++ Radar Control Application to Red Hat Linux.
•          Reverse-engineered 20-year-old M68000 assembly and rewrote it in C for DSP platform.
•          Wrote JAVA server/C# client software for automated test equipment for radar system.
•          Led team which selected new H/W & S/W for real-time embedded radar signal data processor.
•          Created UML 2.0 models to document legacy embedded software.
•          Ensure software compliance utilizing static & dynamic bug detection software.
•          Testing of new and legacy code utilizing automated tools and manually written test.
•          Automation of testing via C++Test by Parasoft which uses JUNIT methodology
•          Perform thorough cleanup of coding, selecting rules for code consistency fixing code to adhere to these guidelines.
•          Preparation to implement automated testing including nightly builds of all code check in.
•          Authored and presented preliminary and critical design reviews for/to external customer.
•          Co-authored SRS, SDD, STD documents for major airborne radar upgrade project.
•          Performed requirements management using DOORS (Telelogic/IBM) software.
•          Part of team which re-engineered land based software for radar system
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Fred', 'Hunter', 'fhunter4@eventbrite.com', '86-(582)172-4931', 'Junior Executive', 0, '2016-03-29 14:02', 'Experience
The Centric Group, St. Louis MO (Contract Position) [05.2011 – Present]
Senior Web Developer
The Centric group is a holding company owned by Enterprise Rent-A-Car. Their business is based on acquiring other companies to build a portfolio of brands and products in the travel accessory, hospitality and correctional industries. I work on a team of talented technologists who provide shared IT services to our brands. Some of my duties and accomplishments include:
•          Helped to establish department-wide standards and best practices for the development team as well as helping create a SharePoint governance plan for the company’s in-development intranet that will service over 3,000 employees.
•          Developed web services to process data and transfer information between various platforms including Oracle E-Business Suite (ERP), IBM iSeries applications and external vendors (Vertex, Paymetric, Mix & Burn) as part of the company’s “integration services” initiative.
•          Developed a hybrid web/mobile site for Victorinox, one of the company’s brands. After scanning a QR barcode on a mobile device, users are taken to a site that determines device capabilities and renders appropriate content, ensuring that the site works on a wide range of mobile phones, tablets and desktop computers.
Environment: C#, ASP.NET 4.0/Visual Studio 2010, SQL Server 2008, Team Foundation Server 2010, Microsoft Office SharePoint Server 2010, Umbraco CMS, HTML, CSS, JavaScript, Amazon EC2
Habanero Computing Solutions, St. Louis MO [01.2009 – 05.2011]
Solution Architect/Senior Developer
At Habanero I worked as an integral part of their web development team. Being that it’s a small company, we had to wear a lot of hats. Some days I would be at sales meetings helping to pitch to new clients. Other days I’d be researching a new e-commerce platform or coding a SharePoint portal site. Some of my achievements include:
•          Whole lifecycle development of several public-facing sites in Microsoft Office SharePoint Server 2007. These sites needed full CMS capabilities as well as integration with other technologies such as forms-based authentication and BI reporting.
•          Playing a key role on strategy projects. One of the services Habanero offers is working with companies who already have development teams to plan large-scale projects. I was the technical architect on some of these projects, and through rigorous requirement gathering have helped design public facing sites, intranet/extranet portals and e-commerce systems, most of which were eventually developed on SharePoint.
•          Architected and developed the global web presence for a large steel manufacturer. The customer has offices in nineteen countries and hired Habanero to build their public-facing site, an e-commerce store and develop an extranet portal.
Environment: C#, ASP.NET 3.5/Visual Studio 2008, SQL Server 2005/2008, Microsoft Office SharePoint Server 2007, Ektron CMS400.NET, Sitefinity CMS, Umbraco CMS, HTML, CSS, JavaScript
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Sean', 'Ward', 'sward6@dropbox.com', '30-(381)782-7752', 'Compensation Analyst', 0, '2016-03-29 14:02', 'StationDigital, St. Louis, MO Oct 2014 – Dec 31, 2014
Java Developer (Consultant)

Responsible for creation of stationdigital.com.
•          Created website using AngularJS, CSS, HTML, and JavaScript.
•          Integrated with Social Media like Facebook and Twitter
•          Added Google Analytics and Ads on the website.


Technical Environment: Java, Tomcat, AngularJS, HTML, CSS, JavaScript, jQuery, MAVEN, MySQL


RGA, St. Louis, MO Oct 2013 – Oct 2014
Java Developer (Consultant)

Responsible for enhancements and support to the Ira application.
•          Batch Processing using PL/SQL.
•          Oracle Performance and Monitoring with OEM(Oracle Enterprise Management)
•          Write SQL Script for Data Mining and Data changes containing over millions of Claims and Premiums.


Technical Environment: Java, Tomcat, JSP, HTML, JavaScript, jQuery, MAVEN, Oracle, PL/SQL


Maritz, Fenton, MO April 2013 – Oct 2013
Java Developer (Consultant)

Responsible for enhancements to the following web applications liste below. I was tasked to move all of the 15-year old technology and inject new technology using AngularJS, jQuery, Bootstrap, and REST. 70% of time was spent working on the Front-end UI using AngularJS, jQuery, Bootstrap, and 30% developing services on the Back-end.

•          Wellness Project
•          Pacific Life
•          Fireman’s Fund
•          Konica/Minolta
•          Kimberly Clark
•          GM
•          Eli Lily
•          Teva


Technical Environment: Java, Tomcat, AJAX, JSP, HTML, AngularJS, jQuery, JBOSS, JUnit Testing, Spring 3.2.2, Hibernate, MAVEN

',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Teresa', 'Reed', 'treed7@lulu.com', '63-(942)392-2790', 'Design Engineer', 0, '2016-03-29 14:02', 'iBridge Solutions, St. Louis, MO Nov 2012 – Present
Consultant
Provide software development consulting services including design and implementation of software systems applying object-oriented design principles and patterns, refactoring, technology selection, and mentoring.
•          MasterCard (11/2012- current) – Member of the Integrated Processing Solutions (IPS) team supporting and enhancing technology solutions for the debit/credit processing business. Extended an existing SMS communications framework to add campaign messaging and improve throughput by introducing multi-threading. Wrote the detailed design and led development on construction of an internal customer sanctions matching system to prevent fraudulent transactions.
Used: JIRA, Eclipse 3.7 (Indigo), Java 6, Hibernate 3, Spring 3, Oracle 11g, JUnit, DBUnit, H2, Visio, Maven, Toad, Redhat Enterprise Linux

Daugherty Business Solutions, St. Louis, MO Feb 2005 – Nov 2012
Senior Consultant
Provide software development consulting services including design and implementation of software systems applying object-oriented design principles and patterns, refactoring, technology selection, and mentoring.
•          Express Scripts (10/2012 – 11/2012) – Attached to the Development Engineering team to perform proof of concept and model implementations for IBM DataPower to Mule migration, BroadVision retirement, and SOFEA development. Also performed services compliance reviews using checklists based on ESI best practices for Java and Mule.
•          Reinsurance Group of America (04/2012 – 08/2012) – Architect on a small team gathering requirements to provide recommendations and planning information for the next generation of an automated underwriting product. Worked with PM and BA to elicit and document functional and non-functional requirements, document a conceptual product architecture, evaluate commercial rules engine offerings for fitness to requirements, recommend deployment options and identify potential platforms for cloud delivery.
Used: MS Word, Powerpoint, Visio
•          Express Scripts (08/2011 – 04/2012) – Technical lead on two Medicare projects implementing web services for Document management and integration with a translation vendor.
Used: Rally, Filenet P8, Eclipse 3.4, Java 6, Hibernate 3, Spring, Oracle 11g, JUnit, Oracle SQLDeveloper, Visio, Maven, Redhat Enterprise Linux
•          Express Scripts (01/2010 – 08/2011) – Implemented prescription data entry and verification functions from user stories. Investigated and corrected defects. Provided deployment and production support.
o        Partnered with the Scrum Master to start an internal study group on Working Effectively with Legacy Code to encourage incremental improvement of test coverage.
Used: Rally, Filenet P8, Eclipse 3.4, Java 5, Hibernate 3, JDBC, Spring, Oracle 10g, JUnit, Oracle SQLDeveloper, Visio, Maven, Redhat Enterprise Linux, bash, awk, perl
•          Express Scripts 04/2009 – 01/2010) – Lead developer on a project to build an integration layer between external clients and the Express Scripts high-volume pharmacies. The approach utilizes multiple JMS queues to induct requests into the system, determine appropriate routing, communicate the orders to the proper pharmacy, provide data services to the pharmacy during the filling cycle, report status back to the originating client, interact with an inventory management system to reserve, reverse and decrement inventory, and produce reconciliation and claim feeds to financial systems. The solution was constructed using Mule ESB essentially as an integration container with services written in Java. Implementation was not test-first but heavily test-oriented using JUnit and Mule FunctionalTestCase tests to exercise application classes and verify Mule service configurations. Development used ActiveMQ for desktop service testing but deployed to WebSphere MQ. Data persistence was implemented in Hibernate to Oracle 10g using annotations from Spring, Hibernate and JPA. The project followed the Scrum process methodology.
o        Analyzed user stories and drew Visio process diagrams to depict message flow and transformation using an Enterprise Integration Patterns template.
o        Wrote a configurable marshalling framework based on the Apache commons beanutils to parse/assemble pipe-delimited messages to/from Java classes.
o        Investigated the workings of the JBossTS transaction server integrated in Mule and developed integration classes that allowed services to utilize Hibernate for persistence with JDBC Connections enlisted in a global XA-transaction started by a Mule endpoint (something neither the client nor the vendor had previously accomplished).
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Jennifer', 'George', 'jgeorge8@indiatimes.com', '63-(931)345-5409', 'Speech Pathologist', 0, '2016-03-29 14:02', 'SniperDyne 2015 – Present
Senior Software Engineer
         Fixed a bug in the FedEx shipping API where commercial addresses were being billed as residential

Applied Logic, Inc. 2014 - 2015
Senior Software Engineer
       Optimized database by analyzing data from SQL Server 2008 DMVs and creating indexes as well as rewriting queries
       Implemented interfaced incubator using JQuery and XML over http and ActiveX control created in C#
       Parameterized and maintained MS Azure PowerShell scripts to create development and test environments
       Added small enhancements and fixed bugs as required

Juggle.com 2011 - 2014
Senior Software Developer
       Optimized modules of the web publishing platform to increase user conversion rates and increased ROAS
       Created proxy to throttle requests to GoDaddy auction API
       Developed services to efficiently retrieve domain information from multiple sources
       Collaborated to develop a distributed application to synchronize data across multiple internal platforms
       Assisted in the configuration of a 3 server HADR cluster hosted on Amazon AWS using SQL Server 2012
       Worked on sites using XML, XSLT, MVC, ASP.Net, jQuery, CSS and SQL Server 2012
       Architected a domain inventory control system with Salesforce integration

Simmons Law firm 2010 - 2011
Senior Software Engineer
        Implemented new features in the case management system
        Worked with users to resolve production issues
        Wrote stored procedures and reports in SSRS
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Barbara', 'Gardner', 'bgardner9@google.es', '86-(124)589-4114', 'Administrative Officer', 0, '2016-03-29 14:02', 'Novaant
IT Systems Architect
				Apr 2012 - Date
•	Managed various vendors
•	Responsible for vendor selection
•	Managed up to 10 direct reports
•	Often hired my own replacement
•	Disaster recovery planning
•	Design and implementation of 2 new virtual environments
•	Design and configuration of Equillogic SANs
•	Accountable for multiple VMware ESXi 4.0, 5.0 and 5.1 virtual environment
•	Responsible for All network infrastructure
•	Design and Implementation of a 300 Server Exchange Environment in Be’er Sheva, Israel.
•	Responsible for Exchange 2007 environments
•	Responsible for Exchange 2010 environments
•	Responsible for Exchange 2013 environments
•	Accountable for over 500 virtual servers
•	Design and implementation of systems
•	Provide second and third tier support for end users.
•	Primary point of contact for major issues
•	Management of external service vendors
•	Provide mentoring for level one and two support agents.
•	Firewall installation and configuration
•	Responsible for the design and configuration of several networks
•	Design and implementation of Active Directory
•	Migration of multiple Exchange servers
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Albert', 'Scott', 'ascotta@hp.com', '63-(355)272-0048', 'Accountant III', 0,  '2016-03-29 14:02', '
Unit4 - Ellisville, MO	12/2015- 3/2016
Business Intelligence / ETL Senior Developer

•	Created ETL software based on Microsoft Integration Services.  This was an original product to facilitate data conversions from one system to another.
•	Design and code reporting and data analysis solutions for the above business objectives. That is, full-stack development from relational database to data warehouse to front-end reporting.
•	Development in the Microsoft Reporting Services using SQL, C#.Net, XML and a variety of application development tools.
•	Followed the Agile method and utilized Axosoft as a project management tool.



Scottrade Securities- St. Louis, MO	2/2010- 11/2015
Business Intelligence Senior Developer

•	Met with business units to determine tactical objectives.  Technical and administrative leadership responsibilities for the Business Intelligence team.
•	Designed ETL solutions for Oracle and SQL Server databases.  These solutions included ETL for a data warehouse, ETL for an Operational Data Store and ETL for database migrations.
•	Responsible for designing, modeling, developing and supporting Data Management Systems and applying data profiling and data analysis in supporting business needs.
•	Used Data Services within the MDM system to compare and properly identify data material.
•	Designed and coded reporting and data analysis solutions for the above business objectives.
•	Development in the Microsoft Business Intelligence Tools (SSRS, SSAS/Cubes, SSIS)
•	Experience working with Informatica for ETL
•	Also, significant database work in SQL and C#.Net.
•	Used Oracle PL/SQL to query and ETL data from Oracle Databases
•	Used Wiki as a reference tool to record information on products.
•	Certified in Agile Methodology


Covenant Technology Partners - St. Louis, MO	 2/2008- 12/2009
Business Technology Architect

•	Responsible for design and implementation of systems for financial and retail industries.
•	Duties included 50 percent hands-on development.
•	Designed ETL solutions for Microsoft SQL Server databases.  These solutions included ETL for a data warehouses and for Operational Data Stores.
•	Some architectural design which included ETL using Data Stage and Informatica.
•	Responsible for designing, modeling, developing and supporting Data Management Systems and applying data profiling and data analysis in supporting business needs.
•	Used Data Services within the MDM system to compare and properly identify data material.
•	Deployed intensive use of ERwin data modeler in the creation of conceptual, logical, and physical data models, database schemas for both operational databases and DW systems running on Oracle 11g and SQL Server 2012.
•	Designed and implemented a data and reporting system for a regional banking client that replaced a legacy system. This project covered a large number of branches in several states and saved the client significant revenue.
•	Development in the Microsoft Business Intelligence Tools (Reporting Services, Analysis Services and Integration Services).
•	Created custom solutions in SQL and C#.
•	Used Oracle PL/SQL to query and ETL data from Oracle Databases
•	Projects worked on used both Microsoft reporting and Business Objects Webi and Universes.
•	Work with IDocs to extract and load information from SAP for reporting.
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Adam', 'Torres', 'atorresb@sbwire.com', '81-(595)796-1207', 'Product Engineer', 0,  '2016-03-29 14:02', ' SACWIS stands for Statewide Automated Child Welfare Information System is a Child benefits application that helps the children of Wisconsin State to receive benefits based on certain state rules. The system allows the county workers and other designated officers to use the application and process for allowing or deny the benefits based on certain rules and conditions.

 Responsibilities: .
•	Analyzed, designed and implemented application based on Object Oriented programming i.e., Object oriented Analysis and Design.
•	 Followed techniques and principles provided by test driven design (TDD) paradigm for developing quality code using Agile methodologies.
•	Documented and communicated application design using UML diagrams like sequence diagrams, class diagrams, use case diagrams.
•	Familiarity with Service oriented architecture (SOA) by developing and consuming Restful web services based on JAX-RS and Jersey, supporting both XML and JSON.
•	Designed the UI of the application using HTML5, CSS3, JSP, JSTL, JavaScript, and AJAX
•	Worked on Java Script libraries using jQuery, Angular JS
•	Implemented views using HTML based design templates for forms, buttons, navigation and other interface components.
•	Implemented various CSS components using bootstrap less stylesheets.
•	Used Bootstrap for user interface elements such as dialog boxes, tooltips, carousels and extended existing interface elements, including  auto-complete function of input fields.
•	Developed application on spring 3.x framework by utilizing its features like Spring Dependency injection, Spring Security and Spring MVC and Spring AOP.
•	Developed the Login, Policy and Claims Screens for customers using AJAX, JSP, HTML5, CSS3, JavaScript, Groovy and JQuery.
•	Used Hibernate Query language (HQL) and the Hibernate Criteria Queries.
•	Used  Mongo Template class to insert and update the Mongo database.
•	Worked on MongoDB as the backend databases.
•	Developed, Tested and Deployed application in WebLogic server.
•	Used Clear case as source control tool.
•	Developed unit test cases and suits on Junit framework for unit testing,
•	Used Maven for building, deploying application.
•	Used Cruise Control for continuation integration environment
•	Used JIRA as project management tool.

Environment: Java 1.7, Agile, Restful web services,  Servlets , Spring, Ajax,  jQuery, JavaScript, AngularJS, JSON, CSS 3, Bootstrap, JSP, JSTL, Hibernate, Junit, Oracle, MongoDB, SQLs, WebLogic, JSON, XML,  SOAP-UI, Clear Case, Clear Quest, JIRA, Eclipse, Maven, HTML5, CSS3.


BCBSFL, Deerwood, FL                                                                                      November 2011 – June 2014
Designation: Senior Java Developer
Project: PIP (Provider information Platform), My Blue Services (MBS)

PIP and MBS were provider and member website applications. PIP provides the information of all providers across Florida State where as members can log in to their account through MBS application and manage their account with BCBS. The project involved a full life cycle implementation of a Knowledge Management Solution for integration and reporting of point of member data across the website across complete Florida State.


 Responsibilities:

•	Followed Test Driven Development (TDD Approach) environment using AGILE methodologies.
•	Developed application using Struts2 MVC architecture.
•	Designed UI for users to interact with system using jQuery, JSON, JSF, Java Script, HTML, CSS, groovy.
•	Used AngualrJS to implement views from HTML templates and routing logic using controllers.
•	Became familiar with using Bootstrap as a frontend web framework to develop responsive web pages.
•	Used AJAX calls for communicating and retrieving from services.
•	 Involved in both developing and consuming Restful web services using Jax-rs and Jersey, supporting both XML and JSON.
•	Used spring framework by utilizing its features like Spring Dependency injection, Spring AOP
•	Worked on Java Message Service (JMS) API for developing message oriented middleware (MOM) layer for handling various asynchronous requests
•	Developed back end business logic with EJB.
•	ANT tool is used for XML mapping and building EAR files.
•	Log4j is used and adopted for the Logging Mechanism.
•	Object Relational Mapping is implemented using Hibernate.
•	Deployed application on WebSEAL  Application Server
•	Developed unit test cases and suits on Junit framework for unit testing
•	Used Jenkins for continuation integration environment
•	Worked on DB2 and MongoDB as the backend databases.
•	Used GitHub as source control tool.
•	Worked on maintenance and enhancement of the project features.


Environment: Java, J2EE, JSP, Servlets, Struts 2, spring, Hibernate 3.0.,EJB,JMS, JSTL, jQuery,  Angular JS, Bootstrap, Groovy, Eclipse, ANT, IBM Web SEAL, DB2 8.1, MongoDB, Agile, IBM Rational Application Developer, JUnit, Log4j, Backbone.js, HTML, CSS, Java Script, JSON, XML, Design patterns, GitHub.
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Pamela', 'Butler', 'pbutlerc@prnewswire.com', '46-(917)172-6878', 'Senior Developer', 0,  '2016-03-29 14:02', '
Software Architect/Analyst at Preferred Resources, contracted to Maritz, Inc.	May 2014 – Present
Software Architect/Analyst
Contract to the Maritz Motivation Company through Preferred Resources as a Software Architect and Analyst, responsible for providing the high level design, time estimates and low level design for software projects.  Also responsible for helping the software support team in determining the root cause and resolution to software issues.
•	Design and deploy the integration of Maritz clients with Maritz’s eCommerce, Order Fulfillment and Point Bank systems.
o	Analyze the client’s web service infrastructure and map to Maritz service bus.
o	Integrate client with Maritz SSO Infrastructure
o	Set up client in eCommerce System
•	Automate the build and deployment of the Maritz Commerce and Fulfillment applications
o	Migrate from Ant and batch file scripts to Gradle
o	Utilize Gradle’s dependency management
o	Utilize Nexus repository
o	Deploy and utilize Jenkins for the building and deployment of software modules
o	Train the Build Specialist on the new system
•	Update Java code from version Java 4 to Java 7
•	Migration of portions of Legacy eCommerce system from JRun 3 to JBoss EAP 6.
•	Provide a plan to update the Software Development Lifecycle to accommodate the new build process, specifically:
o	Utilize a branching strategy
o	Create a deployment strategy
o	Document new build and deployment system
o	Obtain buy-in from directors and other architects
Technologies Used: Java, JBoss, Oracle, Jenkins, MS Team Foundation Services, Nexus, JRun, Visual Studio, C#, CORBA, HP Unix

Senior Software Engineer, Save-A-Lot	August 2012 – May 2014
Senior Software Engineer
As a Senior Software Engineer at Save-A-Lot, responsible for mentoring junior developers and leading development on projects. Also responsible for assigning daily development tasks to other developers on the team and ensuring code quality and on time delivery.
•	Lead the migration of source control management from Serena Dimensions to Collabnet TeamForge and Subversion
•	Lead the migration from an internally developed build environment based on Ant to a new build environment based on Maven and utilizing Jenkins
•	Lead the upgrade and code migration of Java web applications and batch jobs from JBoss 5 and Java 6 to JBoss 7 and Java 7.
•	As part of the JBoss upgrade, designed and implemented framework code to leverage Save-A-Lot’s existing security infrastructure for new JSF applications
•	Consistently sought as a knowledge resource by other developers and team leads to help with performance issues, SQL queries and overall Save-A-Lot system knowledge
Technologies Used: Java, JBoss, MS SQL, Oracle, DB2, PostgreSQL, Collabnet TeamForge, SVN, Serena Dimensions, Maven, Ant, JSF, Flex, Java EE, RedHat Linux, Windows Server, Eclipse, Visual Studio, VB6, C#, Appworx job scheduler, IBM Unix, Jenkins, HTML, Javascript, Python, Spring (exploratory), Hibernate (exploratory), GWT (exploratory)
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Ernest', 'Morris', 'emorrisd@blogs.com', '81-(785)122-6062', 'Graphic Designer', 0,  '2016-03-29 14:02', ' Monsanto, Maryland Heights, MO						 Mar14—Present
Role: Application Developer
Description: Apollo Field Manager/REST API’s
Monsanto has risen to leadership status in the biotech industry through its ability to turn cutting edge science into strategic advantage. A great deal of this success was facilitated through the analysis, collection and reporting tools developed by Technology Pipeline Solutions. One tool used by TPS to provide these solutions is Apollo. The term Apollo can be used to reference the Apollo Application or the Apollo Framework. Apollo the application is a single desktop application with multiple plugins. It provides a single user interface shared by many of our Pipeline applications. The Apollo Framework is used to develop the Apollo application.

Key Contributions
•	Develop and maintain customer facing web applications for Field Manger and Trait Stacking business on the enterprise Java platform.
•	Responsible for design, development, implementation and maintenance of Java/J2EE based large-scale enterprise level applications.
•	Participate in User Requirement Analysis and review of Business Requirements from Functional Architects.
•	Develop application code using Java,Scala, J2EE, Spring, Restful Webservices, SOAP, EJB, JMS, Hibernate, Underscore, backbone,Spring-data-Jpa and XML.
•	Template Builder Application has been implemented using backbone.js for rich client-side application and also to connect the REST ful services for saving the data.
•	Used Mockito for Unit testing.
•	Implemented Automation testing for the applications to kickoff regression and smoke testing after the release.
•	Implemented REST API’s for the Breeder’s in the organization to make their workflow easier and yields a high productivity.
•	Used Swagger Doc to document the designed API’s.
•	SoapUI has been used to test the implemented webservices and also to perform Load Testing.
•	Design and Develop Code using the Test Driven Development (TDD) process with incremental software change supported by the Test, Code, and Re-factor paradigm using Agile Methodology.
•	Solve Complex Business Problems using various Algorithms implemented through simplistic design aligned with well known Design Patterns.
•	Used Jprofiler for profiling, resolving the performance issues and to find out the memory leaks.
•	Perform Refactoring (change internal structure of software) on code based to reduce code complexity and increase code extensibility and maintainability.
•	Develop Backend Data Access Services using J2EE container resources that read and write business data from Oracle Relational Databases.
•	Interact with QA-Testers to solve test platform and workflow issues.
•	Apache Maven has been used to build the application.
•	Maintain Continuous Integration on Team City deployment Servers.
•	Provide On calls Support for the Production issues.
•	Used SVN and GIT as version control and also involved in migrating the projects from SVN to GIT.

Environment: Java 1.7/1.6, Spring MVC, Spring-data-Jpa,SOAP and RESTWebServices,Monsanto cxf,Swiss XML,Java Swing, JGoodies,Backbone js,Underscore js, Weblogic 11, Tortoise SVN,GIT, Source Tree,JIRA, TeamCity, IntelliJ IDEA 13.0.2,Apache Maven 3.2.2,SoapUI.


BrownGreer,Richmond ,VA                                                                                             July13—Jan14
Role: Sr Java Developer
Description:
The project Deepwater Horizon ("DWH") is the official way for Individuals and Businesses to file claims for costs and damages incurred as a result of the oil discharges due to the Deepwater Horizon Incident on April 20, 2010 ("the Spill"). The DWH is administered by the Claims Administrator, who is responsible for all decisions relating to the administration and processing of claims submitted to the DWH.
Key Contributions
•	Involved in various JAD sessions to gather the requirements and how to implement them.
•	 Used SCRUM methodology to emphasize face to face communication and make sure that iteration is passing through full SDLC.
•	The design pattern used to implement web-based systems is Model View Controller
Architecture using Struts2 framework.
•	Handled assignments in developing UI pages for Claim reviews and Audit review modules using JSP, JavaScript, JQuery, CSS, HTML and DHTML technologies.
•	Client side validations were done using JavaScript.
•	 Involved in writing the exception and validation classes using Struts validation rules.
•	Involved in coding for DAO Objects using DAO design pattern.
•	Consumed external web services by creating service contract through WSRR(Websphere Service Registry and Repository) from different Development centers(DCs) and validated the services through SOAP UI.
•	Developed Apache CXF service Interfaces for implementing Route between web Services to provide the Claim information to the clients based on their claim type.
•	Integrated the web services using Apache Camel Routing for generating the new login ids for the users.
•	Designed and developed Hibernate components and mappings corresponding to the tables.
•	Used Hibernate Query Language and Hibernate Criteria API Queries to do the database
Operations.
•	Hibernate Criteria API is used for dynamic query generation to get multi-criteria search
Functionalities.
•	 Hibernate template is used for getting only the desired result instead of an entire object.
•	Spring framework is used to implement Inversion of Control (IOC for dependency
Injection), and Aspect Oriented programming (AOP).
•	Implemented Struts tiles framework for rendering the composite pages.
•	Developed JSP custom tags, using JSP2.0, for encapsulating presentation logic in concise
Form.
•	Written SQL queries and Stored Procedures with oracle database.
•	Creating and Designing XML schemas, creating simple and complex types, global element references, attribute, transform the xml to Java objects using transformer objects.
•	 Test cases were written by using JUnit framework.
•	Web logic server is used for deploying generated EAR’s.
•	Oracle SQL developer is used as a tool for database development.
•	Log4j is used a logging tool for logging application info and exceptions.
•	Clear Case is used as a version control tool.
•	Clear Quest is used for defect tracking.
•	Apache Ant is used as build tool.
•	Interacted with Business Analysts for obtaining the requirements and validating the Implementation of the requirement.
Environment: Java 1.6,Struts 2.0, AJAX, XML, Spring 2.5,Hibernate 3.0,WebServices, Web Logic10.3, JUnit4, Oracle 10g,PL/SQL, Eclipse , Clear Case, Clear Quest, Unix.
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Ruth', 'Andrews', 'randrewse@spotify.com', '380-(472)135-1796', 'Design Engineer', 0,  '2016-03-29 14:02', ' MasterCard Worldwide (OFallon, Missouri) November 2010-present
Project Manager, Senior Systems Analyst, and Web/Omniture Analyst

Job Responsibility:
·        Strategically align with Customers/Business/Product Owners to create Business Requirements/Stories. Create Functional Requirements for Development team.
·    Create and implement test cases for regression, UAT, and system testing.
·    Manage 8 applications and coordinate 10-15 projects at a time with development teams in 4 different regions. Plan resources accordingly and highlight risks.
·    Create milestones and focus on tasks. Assist in lowering costs by assuring that development delivers the desired needs within the estimated timeframe
·    Resolve customer issues quickly and effectively.
·    Use a hybrid of waterfall and Agile (Scrum and ADAPT) approach.
o        Help Product Owners create User Stories and Acceptance Criteria.
o        Create backlog and help prioritize in moving the stories forward. Test that all acceptance criteria has been met before moving story onto the Product Owner.
o        Help facilitate Stand Up Meetings with all parties.
o        Involved with Sprint Planning and demos every two weeks.
·    Develop and implement Javascript tagging for agencies and internal customers.
·    Support development team on implementing Omniture.
·    Create Omniture reports that guide Business Owners to understand their KPI.
·   Wrote SQL queries to retrieve data from database tables via TOAD.
Tools: MS Project Plan, MS Visio, TOAD, QTP, Quality Center, Omniture/Site Catalyst, JavaScript, Fusion Tools for Scrum and ADAPT methodology, ChangePoint, GSM, SQL

Bashundara Group (Dhaka, Bangladesh):
Job Title: Business Analyst and Project Manager
Period: September 2009-July 2010
Job Responsibility:
·         Develop functional, system, and technical design specifications.
·         Review and edit requirements, specifications, business processes, and recommendations for RFP and issues that may arise.
·         Identify issues, risks, and dependencies.
·         Enforce project deadlines and schedules, focusing on tasks and milestones.
·         Coordinate and consolidate 5 or more projects at a time.
Tools: Oracle 10g, MS Project, Navigator, Excel (Macros)
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Jimmy', 'Wood', 'jwoodf@cargocollective.com', '256-(819)355-7417', 'Associate Professor', 0,  '2016-03-29 14:02', ' Front-end Developer
•	Member of the Implementation Team, responsible for specific (Enterprise) customers regarding the front-end implementation (and aftercare) of their community platforms, which are used by millions of users.
•	Together with my direct colleagues (including designers, project implementation manager and more) I work in an agile mode (short lines) to ensure that the project is successfully implemented and the customer is happy.
•	I ensure that specific designs are implemented using HTML5/CSS3 and Javascript in combination with the standardized inSided platform components using Twig templates.
Moosylvania Marketing
Maplewood, MO, United States June 2013 - June 2014
Front-end Developer
•	Responsible for creating, maintaining and troubleshooting web sites for various clients.
•	Used HTML, CSS, JavaScript & PHP to create & maintain sites.
•	Utilizing the Responsive Design technique for coding my sites as well as my HTML emails.
•	I have developed in the SilverStripe platform & used GIT for version control.
•	I have developed several eBlasts (HTML email) using Campaign Monitor.
Savvis Direct
Town and Country, MO, United States May 2013 - June 2013
Front-end Developer (TEKsystems contractor)
•	Using AngularJS, HTML & CSS (SASS/Bourbon) to redevelop the control panels for savvisdirect customers cloud-based servers.
•	Working in a Linix (ubuntu) environment.
Hello Agency
Brussels (Haren), Belgium Feb 2013 - May 2013
Front-end Developer (contractor)
•	Worked as a contractor for ConSol Partners at Hello Agency.
•	Created a site making extensive use of jQuery animation techniques.
•	Worked on a sited which was loosely based on the Ascensor javascript framework.
•	Applied a responsive-design layout to an existing retail site for mobile devices.
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Peter', 'Harvey', 'pharveyg@list-manage.com', '1-(777)427-9855', 'Safety Technician I', 0,  '2016-03-29 14:02', ' Savvis, St. Louis, Mo.
April 2012 - present
Senior Software Engineer
        Developed custom modules from scratch for Drupal 7 using PHP and MySQL.
        Managed continuous integration with Jenkins.
        Wrote Bash scripts to automate deployment and continuous integration.
        Developed a Zend MVC REST API to remotely control processes on several servers.
        Wrote functional tests using CasperJS and PhantomJS.
        Wrote unit tests with PHPUnit.
        Worked with Git and Subversion source control.
        Administered CentOS servers and Ubuntu workstations.

Osborn & Barr, St. Louis, Mo.
September 2011 - March 2012
Senior Technology Manager
        Lead developer on a PHP Zend MVC application to manage complex PR processes for Monsanto.
        Responsible for basic system administration on CentOS servers.
        Developed and implemented coding standards for PHP, HTML, CSS, and SQL.
        Moved all developers from developing on live servers to segregated local, qa, and production environments.
        Interviewed and hired web developers (LAMP) and web designers (HTML & CSS).
        Implemented git version control for all current projects, and trained developers who had not worked in a distributed VCS.
        Created a system to document and track technical policies and procedures, resulting in better team knowledge sharing and to simplify training new hires.
        Implemented and led weekly code review sessions to facilitate collaboration and encourage constructive criticism.

Announce Media, St. Louis, Mo.
November 2008 - August 2011
Application Developer
        Designed and built sites with extremely high traffic (90 million hits/month across ~20 sites).
        Worked on all aspects of front-end site development from CSS and “partial” design to MVC data access models.
        Developed bash shell scripts to automate workflow.
        Worked with external and internal XML REST APIs.
        Built sites using a heavily modified Zend MVC framework.
        Worked with PHP, MySQL, jQuery, and Apache on Red Hat, CentOS, and Ubuntu Linux.
        Worked extensively with git and subversion version control systems.
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Elizabeth', 'Ryan', 'eryanh@mapy.cz', '86-(994)173-6624', 'Mechanical Systems Engineer', 0,  '2016-03-29 14:02', ' Java Design and Development (Web and IVR)

Responsible for IVR and Java design and development for the IPS Prepaid and Debit Platforms.
•	Lead team of 5 responsible for design, development and maintenance of core functionality to MasterCard Prepaid and Debit Processing Platform:  All IVR Functionality, Web Shopping Cart, Web and IVR Pin Functionality.
•	Designed new IVR system leveraging current web architecture to ensure maximum code reuse.
•	Designed and developed new IVR system using Java, TILES, Spring Web Flow and VXML.
•	Responsible for Voice Interface Design, system design and implementation of all IVR functionality.
•	Integrated IVR technologies with complex security requirements using Webseal EAI Authentication.
•	Designed and developed web PIN PAD solution for MasterCard. First PIN web solution to be approved by MasterCard Security.
•	Designed and developed mechanism to reveal users pin through the web and IVR. First reveal functionality approved by MasterCard Security.
•	Provided Change Management support.
•	Responsible for troubleshooting and fixing production issues (on Call 24 X7).
•	High knowledge of system architecture and availability through the web layers.
•	Key management functions and troubleshooting for all of the web layers.
Environment: 4 Intervoice IVR servers interfacing with 4 Websphere clones, using WebServices to connect to multiple backend mainframes, and Hibernate framework for DB2 and Oracle connections.

AT&T - St. Louis, MO 	06/2004 – 02/2008
Java Developer (IVR)
Responsible for Java development in AT&Ts high profile project NCSC which InfoWorlds editorial staff rated in the Top 10 of 100 companies projects spotlighted for making the best use of technology to enhance their business.
•	Managed SCM process for the team, used CVS for version control.
•	Provided tier 3 support for high volume (250,000 calls per day) nationally distributed application.
•	Developed code using JSP, Java classes, and design patterns for implementing new functionality to IVR.
•	Developed XML and VXML code to implement DTMF and Speech Recognition IVR.
•	Developed and modified Ant Script for easier builds.
•	Provided technical support and mentoring to other team members.
•	Responsible for System Feature Design, Detail Design, Development and Unit testing for projects in the IVR.
•	Interfaced with CTI to pass data attached data to agents in the call center.
Environment Clustered nationally distributed with 64 Websphere clones on UNIX servers interfacing with 107 Nortel IVR Servers.
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Michelle', 'Anderson', 'mandersoni@plala.or.jp', '54-(161)559-1996', 'Design Engineer', 0,  '2016-03-29 14:02', ' Enterprise Fleet Management - St. Louis, MO  09/2011 – Present Senior Software Engineer Senior Java web developer in an Eclipse / WebLogic development environment.  Lead integration efforts between Salesforce.com instance and EDGE using Informatica Cloud and plugins developed in Informatica PowerCenter.  EDGE is a backend system that provides a central repository of all of customer, quote, or vehicle information from the prospect phase to the customer activation phase.   EDGE supports complete fleet management, including master data entry, customer management, quote, orders, vendor management, and lease management.  Provided oversight of development efforts of CSC, Salesforce and Informatica Professional Services consultants working on site, remote and off shore.
Environment : Salesforce, Informatica Cloud, PowerCenter, Java, JSP, WebLogic, Oracle 11g,
ClearCase, Struts, Hibernate, Log4j, SQL and Ant. Senior Java web developer in an Eclipse / WebLogic development environment.  Team contributor on rewrite of existing License, Tax and Title(LTT) application.
Environment :Java, JSP, Spring, Struts, Hibernate, jQuery, jqGrid, WebLogic, Oracle 11g,
ClearCase, Log4j, SQL and Ant. Onshore Technology Services - St. Louis, MO  06/2010 – 09/2011
Java Web Developer – MasterCard Java web developer in an Eclipse / IntelliJ development environment.  Develop web applications for several clients managing credit card account usage and reporting.  Customize business flow and web presentation using JBoss and BIRT reporting engine.  The application (Smart Data) is used by clients to manage corporate expenses by giving them the ability to create reports that more closely align with their internal structures.  This application is also rebranded and sold to other companies to provide to their clients.
Environment: Java, JSP, JBoss, Oracle 10g, SQL, BIRT Reporting Engine, Subversion, Jira, Hibernate
Team Lead / Java Web Developer - Centene Coporation Lead four teams of Java web developers in a RAD development environment.  Develop portal applications for several clients managing Medicaid, Medicare and other state sponsored programs.  Customize business flow and web presentation using WebSphere environment.  The applications are used by members and providers to manage claims, authorizations and other health care information.  Responsible for client engagement management and team leadership of both client site developers and remote developers in St. Louis, Joplin, Lebanon and Springfield, Missouri.
Environment: Java, JSP, WebSphere Portal, WebSphere Application Server, Oracle 10g, SQL, IBM - Rational Application Developer, Subversion, Hudson, Hibernate
Development Studio, LLC- St. Louis, MO  11/2009 – Present Android Development Designer and developer for Preflop Drill Sergeant (PDS).  PDS is an Android 2.0 application that using a SQLite database to allow users to practice a narrow aspect of the Texas hold ‘em poker game on a mobile device.  Responsible for the design of the overall project, database design, UI graphic design and Java coding.
Environment: Java, SQLite, SQL, Eclipse with the Android ADT plugin as IDE
',1,1);

insert into applicant (first_name, last_name, email_1, cell_phone, title, is_deleted, created_date, resume,applicant_status_id,work_status_id) values ('Gerald', 'Cooper', 'gcooperj@abc.net.au', '86-(173)949-5106', 'Software Consultant', 0,  '2016-03-29 14:02', ' E4Sciences 2014 – Present (Rhode Island / Newton, CT)
•	Android (SDK) Application Developer for Schlumberger Quarterly Periodical w/ Pictures & Multimedia Content
•	Maintain Stash repository via Eclipse IDE
•	Worked with Android Notification Libraries, specifically Parse and UrbanAirship
•	Link to Schlumberger App: https://play.google.com/store/apps/details?id=com.slb.android.oilfield_review

Mesa Labs 2015 (Rhode Island)
•	Android (SDK and Android Studio) Application Developer created and published Battery Calculation app using Android Studio
•	Link to Mesa Labs App: https://play.google.com/store/apps/details?id=com.mesalabs.datatracebatterylife

Ozmott, LLC 2011- 2014 (Rhode Island)
•	Android (SDK) Application Developer, using REST services, responsible for bringing app to market and for all further enhancements
•	Developed Android application using GIT Hub, Apache HTTP Library, and Google Volley Library.
•	Tested iPhone and Android applications as well as Back-end Server product as Lead Tester.
•	Participated in Agile-like methodology that included daily standups and “Sprint”
•	Link to Ozmott App: https://play.google.com/store/apps/details?id=com.ozmott.android.ozshop&hl=en

Raytheon 1997- 2010 (Rhode Island / Aurora, CO)
•	Produced a state-of-the-art sonar for U.S. Navy Zumwalt Destroyer Project by designing firmware, using ‘TI - C’, on TMS320C6455 DSP processor with Code Composer 3.1/3.3 for transmission board that communicates with controlling software ensembles on IBM Blades/Linux platform.
•	Provided weather professionals with advanced information to determine the Safe launching of NASA Shuttles and Vandenberg Satellite launch vehicles as Lead Software Engineer for Weather Instrumentation Ingest software, written in C++, C, and JAVA, for Air Force Project
•	Developed Earth Satellite (EMOS) ground based Satellite Command and Control Software using C++ and C on Windows based PC for NASA maneuvering and the sending and receiving of telemetry data that was useful for long-term global observations of the land surface, biosphere, atmosphere, and oceans of the Earth.
•	Created Communication Satellite (SuperBird 6) ground based Command and Control Software using C++ and C on Windows based PC for a satellite made by Boeing for a Japanese Telecommunications/Defense company commanding position maneuvering, data encryption, and the sending and receiving of business telecommunication services.
•	Lead Team Developer, participating in all phases from generating requirements using DOORS and Rose UML, C++, C, JAVA, code development, to final integration and Unit testing of U.S. Navy Zumwalt Next Generation Destroyer Sensor software ensembles that produced a next-generation Sonar to detect enemy targets.
',1,1);
