# directory-service

This is a java utility to find domain's group and their group members in hierarchy format.

### Requirement 

1. Install Java 1.8 +.
1. Executable directory-service-0.0.1.jar file.
1. Client_Secret file.
1. Gsuit admin permision.

### Setup

1. Download java 1.8 + Version. click [here](https://java.com/en/download/)
1. Create directory in c:\directory-service
1. Download executable directory-service-0.0.1.jar file. click [here](https://drive.google.com/file/d/1Oiek5w8OHORx4_xL4D1sU3-U8j_ZZ40p/view?usp=sharing)
1. Get Client ID from [Google Developer Console](https://console.developers.google.com/)
1. Create new project of select exists project
1. Click "Create new Client ID" in Credentials tab
1. Select "Installed application" and "Other" application type then click "Create Client ID"
1. Click "Download JSON" and save downloaded JSON file to c:\directory-service\client_secret.json
1. Go to "API" page in left side menu
1. Enable "Admin SDK", "Groups Settings API" and "Calendar API"

### Mode :

The utility can be executed in an interactive or silent mode using following commands.

#### Interactive mode :     
```sh
cd c:\directory-service\
java -jar directory-service-0.0.1.jar -i
```

1. domain_name           : reancloud.com
1. user_name             : Nikhil.Kapure
1. client_secret         : /home/rean-cloud/client_secret.json
1. output_json_dir_path  : /home/rean-cloud

#### Silent mode : 

java -jar directory-service-0.0.1.jar domain=<domain_name> user_name=<user_name> client_secret=<client_secret> output_json_dir_path=<output_json_dir_path>
    
Example : 
```sh
java -jar directory-service-0.0.1.jar domain=foo.com user_name=nikhil.kapure client_secret=c:\directory-service\client_secret.json output_json_dir_path=c:\directory-service
```
## Contributing

  1. Fork it ( <https://github.com/NikhilKapure/directory-service/fork> )
  1. Create your feature branch (`git checkout -b my-new-feature`)
  1. Commit your changes (`git commit -am 'Add some feature'`)
  1. Push to the branch (`git push origin my-new-feature`)
  1. Create new Pull Request

### License and Author

* Author:: Nikhil S. Kapure (<nkapure11@gmail.com>)
