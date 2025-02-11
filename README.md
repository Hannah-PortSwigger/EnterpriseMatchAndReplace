# Enterprise Match and Replace
Extension to match and replace content in outgoing requests in Burp Suite Enterprise Edition.

## Usage
1. Build the extension or use the latest release.
2. [Load the extension into Burp Enterprise](https://portswigger.net/burp/documentation/enterprise/working/scans/extensions), and add the extension to your Site Details page
3. [Configure your session handling rule](#configuring-your-session-handling-rule-in-burp-suite-professionalcommunity) in Burp Suite Professional
4. [Import the scan configuration into Enterprise](https://portswigger.net/burp/documentation/enterprise/working-with-scans/scan-configurations#importing-scan-configurations) and add the scan configuration to your Site Details page

### Configuring your session handling rule in Burp Suite Professional/Community
1. Add a session handling rule to Burp Pro/Community that invokes the Burp extension.
   - Build the extension JAR or use the latest release
   - Load extension into Burp Suite Professional or Community Edition (Extensions > Installed > Add) *Ignore any errors on load*
   - Go to "Settings > Sessions > Session handling rules > Add > Rule actions > Add > Invoke a Burp extension > Extension action handler" and select this extension from the list
3. Provide the description for the session handling rule in the following format:
```json
{"matchers":[{"match":"MATCH1","replace":"REPLACE1"},{"match":"MATCH2","replace":"REPLACE2"},{"match":"MATCH3","replace":"REPLACE3"}]}
```
*N.B. You can provide new lines in "replace" by using "\n".*\
*N.B. You can use regular expressions in the "match" fields.*
3. Set scope for session handling rule appropriately.
4. Reload extension in Burp.
   - You can quickly reload an extension by going to "Extensions > Installed" and using Control/Command + Click on the "Loaded" checkbox
5. Check that "Extension > Output" tab has recorded your match and replace rules appropriately.
6. Test the extension is replacing content appropriately by checking the relevant request in Repeater.
6. Export session handling rule from Burp and import to Enterprise
   - Export from Burp: "Settings > Sessions > Session handling rules > Cog button > Save settings"
   - [Import to Enterprise](https://portswigger.net/burp/documentation/enterprise/working-with-scans/scan-configurations#importing-scan-configurations)

## Example JSON configuration file for Enterprise upload
*Note: This session handling rule configuration is in-scope for all URLs*
```json
{
  "project_options":{
    "sessions":{
      "session_handling_rules":{
        "rules":[
          {
            "actions":[
              {
                "action_name":"Match and replace",
                "enabled":true,
                "type":"invoke_extension"
              }
            ],
            "description":"{\"matchers\":[{\"match\":\"MATCH1\",\"replace\":\"REPLACE1\"},{\"match\":\"MATCH2\",\"replace\":\"REPLACE2\"},{\"match\":\"MATCH3\",\"replace\":\"REPLACE3\"}]}",
            "enabled":true,
            "exclude_from_scope":[],
            "include_in_scope":[],
            "named_params":[],
            "restrict_scope_to_named_params":false,
            "tools_scope":[
              "Target",
              "Scanner",
              "Intruder",
              "Repeater",
              "Sequencer"
            ],
            "url_scope":"all",
            "url_scope_advanced_mode":false
          }
        ]
      }
    }
  }
}
```
## Limitations
- Session handling rule description must be on one line
## Future improvements
- Support more special characters (e.g. \t)
- Throw more descriptive error messages.