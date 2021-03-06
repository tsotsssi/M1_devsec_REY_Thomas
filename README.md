
# M1 Secure Development : Mobile applications
## REY Thomas IOS3
##
##
## - Explain how you ensure user is the right one starting the app
#### When the application start, an authentication screen is displayed and the user must authenticate as if he wanted to unlock his phone.
#### The authentication screen is the same as the default lock screen of the user.
#### If it's a fingerprint authentication by default it will asks for his fingerprint, if it's a PIN authentication it will asks for his PIN code, etc...
##
##
## - How do you securely save user's data on your phone ?
#### The accounts are just displayed when the user is succesfully authenticated, it does not store the accounts' data on the phone.
#### Each time the user wants to refresh, it deletes the displayed accounts and asks again for authentication.
##
##
## - How did you hide the API url ?
#### It is not possible to hide perfectly a string in the source code and make it impossible to find but I tried to make the reverse engineering very difficult.
#### I encrypted the API url with an XOR function before release so the the API url is never stored in clear in the release source code.
#### In the source code, the API url is an array of integer that must be decrypted with the correct key and the correct function to obtain the url string.
#### I also obfuscated the code in the release apk to make the reverse engineering more difficult.
##
##
## - Screenshots of your application 
#### Opening of the app:
![Capture_1](https://user-images.githubusercontent.com/75266416/110215935-9c7f5f80-7eac-11eb-904e-521014b981ef.png)
#
#### Ask the PIN code, password, pattern or fingerprint of the user according to his default lock screen authentication option:
##### (If none it asks the user to activate it)
![Capture_2](https://user-images.githubusercontent.com/75266416/110216092-7ad2a800-7ead-11eb-85b8-0943874c9a5d.png)

![Capture_3](https://user-images.githubusercontent.com/75266416/110215928-99846f00-7eac-11eb-807d-7e7bf8ae18dd.png)
#
#### If the user successfully authenticates a welcome message is displayed:
![Capture_4a](https://user-images.githubusercontent.com/75266416/110215932-9ab59c00-7eac-11eb-9095-8a73f02a6f52.png)
#
#### When the user click on refresh it shows again the authentication screen.
#
#### If the user canceled or failed the authentication the request to the API does not start and the accounts are not displayed:
![Capture_4b](https://user-images.githubusercontent.com/75266416/110215933-9b4e3280-7eac-11eb-8d96-228327ca46f2.png)
#
#### If the user successfully authenticates an https request is made with the tls protocol to the API url.
#### Then the accounts are displayed and the user can scroll through:
![Capture_5](https://user-images.githubusercontent.com/75266416/110215934-9be6c900-7eac-11eb-8942-ab65ebd2d53a.png)
