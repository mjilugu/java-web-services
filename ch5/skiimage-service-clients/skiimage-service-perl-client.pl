#!/usr/bin/env perl -w

use SOAP::Lite +trace => 'debug'
use strict;

my $url = "http://localhost:9876/ski?wsdl";
my $service = SOAP::Lite->service($url);
print $service->getImage("nordic"), "\n"; # base64 string
