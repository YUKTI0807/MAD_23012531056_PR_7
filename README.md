## Practical-7 : Develop an Android application that retrieves person data in JSON format from an internet API and stores the retrieved data in an SQLite database.
JSON Format, ListView, RecyclerView, HttpURLConnection, CoroutineScope

Create an application to create JSON URL for Contact which have field(id, Name(First Name, Last Name), Phone No, Address)

To Generate JSON Data, Refer: https://app.json-generator.com/

1. Create MainActivity according to below UI design.

2. Use link generated from website for JSON Data

3. Create Class Person with member Variables like id, Name, Phone No, Email Id, Address, Latitude, Longitude. This class should be inherited from Serializable class.

4. Generate JSON data format according to below image.

5. Use RecyclerView or ListView Adapter

6. Add Internet Permission in  Manifest file

7. Create Class HttpRequest for communicating with Web URL

## How the UI looks Likes?

| JSON Generator `CODE` | JSON Generated `JSON` | In App Syncing |
|---|---|---|
| <img width="592" height="750" alt="Screenshot 2025-11-15 at 9 18 07 PM" src="https://github.com/user-attachments/assets/71eee898-d519-4584-8561-eebd0fed7131" /> | <img width="592" height="750" alt="image" src="https://github.com/user-attachments/assets/2ad86775-26c0-466c-8243-cdd267de81db" /> | <img width="592" height="750" alt="Screenshot_20251115_220001" src="https://github.com/user-attachments/assets/894cc809-c8b4-49ea-a4c1-306cad098001" /> |


JSON : https://api.json-generator.com/templates/kvHxFgDYej7S/data <br>
TOKEN : glyycxuoph770p3kybgnvuvsyyqk0yizez16mead
