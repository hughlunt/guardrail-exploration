# Contributing

## Building the application

### Prerequisites

**Scala 2.11.8** which requires Java 8.

### Create application database

Make sure to update your MySQL configuration file (`my.cnf`) to include:
```
default-time-zone='+00:00'
```

Database creation script:

```
DROP DATABASE site_invoices;
CREATE DATABASE site_invoices DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP USER siteinvoicesuser@localhost;
FLUSH PRIVILEGES;

CREATE USER siteinvoicesuser@localhost IDENTIFIED BY 'siteinvoicespassword';

GRANT ALL PRIVILEGES ON site_invoices.* TO siteinvoicesuser@localhost IDENTIFIED BY 'siteinvoicespassword' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON site_invoices.* TO siteinvoicesuser@127.0.0.1 IDENTIFIED BY 'siteinvoicespassword' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```

Set `max_allowed_packet` mysql variable to 8MB.
Locate and edit mysql config file. (If you've installed mysql with Homebrew on OS X it should be visible under `/usr/local/etc/my.cnf`)
Insert a new line:

```max_allowed_packet=8388608```

## Running the application

### Environment variables

```
export SITEINVOICES_DB_HOST=<db server host name/address>
export SITEINVOICES_DB_NAME=<db name>
export SITEINVOICES_DB_USER=<db username>
export SITEINVOICES_DB_PASSWORD=<db password>
export SITEINVOICES_HTTP_PORT=<http server port>
export MAUTH_APP_UUID=<uuid of the application, registered with mAuth>
export MAUTH_URL=<url of the mAuth authentication server>
export PRIVATE_KEY=<mAuth private key>
export PUBLIC_KEY=<mAuth public key>
```
