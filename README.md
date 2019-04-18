# photo_renaming
Rename photos based on their date taken. Just drop all pictures of your camera, phone and WhatsApp into one directory and all photoas are named and sorted accordingly with one click.

# Renaming strategy

| Original name              | Date taken          | File created |  New file name              | Notes         |
| ---------------------------| ------------------- | ------------ | --------------------------- | ------------- |
| 1970-01-01 14.15.16 (Name) | 1970-01-01 14.15.16 | any          | 1970-01-01 14.15.16 (Name)  | Unchanged     |
| any (Name)                 | 1970-01-01 14.15.16 | any          | 1970-01-01 14.15.16 (Name)  | Name is kept  |
| any                        | 1970-01-01 14.15.16 | any          | 1970-01-01 14.15.16 ()      | Date taken    |
| IMG-20181221-WA0016        | any                 | any          | 2018-12-21 (WA0016)         | WhatsApp      |


# Known issues to be addressed
* Time of video files is not correct. 
