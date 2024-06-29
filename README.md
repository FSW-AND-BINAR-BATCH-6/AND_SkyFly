
# Binar Final Project - Skyfly Android

<div align="center" width="100%">
    <img width="100%" height="100%" src="https://res.cloudinary.com/dtrnc4nz4/image/upload/v1719671375/assets/dmrraphwgsdnbigftdvu.png" alt="Design Spread" title="Design Spread"> 
</div>
<br>


### Team C-1 Skyfly!

Skyfly API allows you to get the needed resources to make Skyfly application run seamlessly. Some of this service is using authentication to access each service. You need to login first to access the service.

### Overview

SkyFly is an online flight ticket booking application designed to provide a seamless and user-friendly experience for booking flights. This project uses ExpressJs as the backend framework and Postgres as the database, managed through Prisma ORM. The application supports various functionalities essential for an efficient flight booking system.

# Data Team C1

|                   |                                                                                                                                             | **LinkedIn**                                                                 | **Github**                                    |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------- | --------------------------------------------- |
| **FSW**           | _Reinanda Faris_                                                                                                                            | [LinkedIn](https://www.linkedin.com/in/reinanda-faris/)                      | [Github](https://github.com/Reinandafaris)    |
|                   | _Viery Nugroho_                                                                                                                             | [LinkedIn](https://www.linkedin.com/in/viery-nugroho)                        | [Github](https://github.com/vierynugroho)     |
|                   | _Andhika Rizky Aulia_                                                                                                                       | [LinkedIn](https://www.linkedin.com/in/andhika-rizky/)                       | [Github](https://github.com/ndikrp)           |
|                   | _Ananda Ias Falah_                                                                                                                          | [LinkedIn](https://www.linkedin.com/in/falahsuryagemilang/)                  | [Github](https://github.com/falahsg)          |
|                   | _Naufal Ady Saputro_                                                                                                                        | [LinkedIn](https://www.linkedin.com/in/naufal-ady-saputro-71050b24b/)        | [Github](https://github.com/naufaladysaputro) |
|                   | _Rizki Mauludin Yoga P._                                                                                                                    | [LinkedIn](https://www.linkedin.com/in/riski-mauludin-yoga-8718602b0/)       | [Github](https://github.com/RMYP)             |
|                   | _Rafi Husein Bagaskara_                                                                                                                     | [LinkedIn](https://www.linkedin.com/in/rafi-husein-257a76291)                | [Github](https://github.com/raisenbk)         |
|                   | _Lowis Armando Hutabarat_                                                                                                                   | [LinkedIn](www.linkedin.com/in/lowis-armando-hutabarat-80b7612b3)            | [Github](https://github.com/LowisHutabarat)   |
|                   |                                                                                                                                             |
| **AND**           | _Komang Yuda Saputra_                                                                                                                       | [LinkedIn](https://www.linkedin.com/in/komang-yuda-saputra-abb21b291/)       | [Github](https://github.com/YudaSaputraa)     |
|                   | _Ihsan Widagdo_                                                                                                                             | [LinkedIn](https://www.linkedin.com/in/ihsan-widagdo/)                       | [Github](https://github.com/dagdo03)          |
|                   | _Bella Febriany Nawangsari_                                                                                                                 | [LinkedIn](https://www.linkedin.com/in/bella-febriany-nawangsari-4642a3291/) | [Github](https://github.com/bellafebrianyn)   |
|                   | _Mochammad Yusuf Pratama_                                                                                                                   |                                                                              |                                               |
|                   |                                                                                                                                             |
| **Project Title** | _SKY-FLY_                                                                                                                                   |
|                   |                                                                                                                                             |
| **Note**          | _Binar KM6_                                                                                                                                 |
|                   | [Trello Team C1 Binar KM6](https://trello.com/c/2XzOhXim/60-c1-binar-km6-fsw-x-and)                                                         |
|                   | [Daily Stand-Up Team C1 Binar KM6](https://docs.google.com/spreadsheets/d/1aCpje7mQnG5uhmBOh9sEThQKYgatLdNpPoSoQK6VUvk/edit#gid=1785037003) |
|                   | [Deployed API](https://backend-skyfly-c1.vercel.app/api-docs/)                                                                              |

---




## Tech Stack

- MVVM (Model View ViewModel)
- Repository Pattern
- Coroutine Flow
- Koin Dependency Injection
- Coil Image Loader
- Retrofit
- OkHttp
- Groupie
- Room
- Chucker
- JUnit 4
- Firebase
- Postman

## How to Use the App

### Initial Setup
- **Onboarding Screen**: The first time you install the app, an onboarding screen will be displayed.
- **Home Tab**: After completing the onboarding, you'll be navigated to the Home tab.

### Home Tab
- **Selecting Flight Details**:
    - Choose the origin and destination.
    - Select the departure date.
    - Specify the number of passengers and seat class.
- **Viewing Available Flights**:
    - Once you fill out the form, you'll be directed to the available flights page.
    - Select a flight to view its details.
- **Flight Detail**:
    - After selecting a flight, you will be taken to the flight detail screen.
- **Authentication**:
    - If you're not logged in, you'll be prompted to log in or register.
    - You can create a new account or log in if you already have one.
- **Ticket and Passenger Form**:
    - Fill out the ticket booking form and the passenger details form.
- **Seat Selection**:
    - Navigate to the seat view to choose your desired seat.
- **Flight Detail Review**:
    - Review the flight details you've selected.
- **Payment**:
    - Proceed to the payment menu, where various payment methods are available, such as transfer and QRIS.
- **Ticket Confirmation**:
    - After completing the payment, you'll see the detailed ticket information.

### History Tab
- **Viewing Ticket History**:
    - All your ticket booking history will be displayed here.
    - Click on any booking to view its details.
- **Transaction Status**:
    - The tab shows the status of your transactions: "PAID", "UNPAID", or "CANCELED".
    - If you have unpaid bookings and want to cancel them, click the "cancel transaction" button below the details.

### Notification Tab
- **Viewing Notifications**:
    - View all notifications related to your account.
    - Click on a notification to see more details.

### Account Tab
- **Viewing and Editing Profile**:
    - View your personal information and edit details such as your name and phone number.
- **Changing Password**:
    - Click the button to change your password.
- **Logging Out**:
    - Use the logout button if you wish to log out of your account.



## SkyFly Api used

#### Login

```http
POST api/v1/auth/login
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `loginRequest` | `object` | **Required**. Login request payload (email, password) |



#### Register

```http
POST api/v1/auth/register

```
| Query         | Type     | Description                      |
| :---------------- | :------- | :------------------------------- |
| `registerRequest` | `object` | **Required**. Register request  payload (FullName, Email, PhoneNumber, password) |



#### Verify Account

```http
PUT api/v1/auth/verified
```

| Query               | Type     | Description                                        |
| :---------------------- | :------- |:---------------------------------------------------|
| `token`                 | `string` | **Required**. Verification token                   |
| `verifyAccountRequest`  | `object` | **Required**. Verify account request payload (otp) |



#### Forget Password

```http
POST api/v1/auth/forgetPassword
```

| Parameter                | Type     | Description                                           |
| :----------------------- | :------- |:------------------------------------------------------|
| `forgetPasswordRequest`  | `object` | **Required**. Forget password request payload (email) |


#### Get All Flight with Sort or Filter

```http
GET api/v1/flights/
```

| Query              | Type     | Description                    |
| :----------------- | :------- | :----------------------------- |
| `search`           | `string` | Search query                   |
| `page`             | `int`    | Page number for pagination     |
| `limit`            | `int`    | Number of results per page     |
| `departureAirport` | `string` | Departure airport code         |
| `arrivalAirport`   | `string` | Arrival airport code           |
| `departureDate`    | `string` | Departure date (YYYY-MM-DD)    |
| `returnDate`       | `string` | Return date (YYYY-MM-DD)       |
| `arrivalDate`      | `string` | Arrival date (YYYY-MM-DD)      |
| `seatClass`        | `string` | Seat class                     |
| `adult`            | `int`    | Number of adult passengers     |
| `children`         | `int`    | Number of child passengers     |
| `baby`             | `int`    | Number of baby passengers      |
| `sort`             | `string` | Sorting order                  |


#### Get Flight by Id

```http
GET api/v1/flights/{id}

```

| Parameter   | Type     | Description                    |
| :---------- | :------- | :----------------------------- |
| `id`        | `string` | **Required**. Flight ID        |
| `seatClass` | `string` | Seat class                     |


#### Get All Airtport

```http
GET api/v1/airports/
```

| Query   | Type      | Description                           |
| :------ | :-------- | :------------------------------------ |
| `city`  | `string`  | City name                             |
| `showAll` | `boolean` | Show all airports (default: true)  |


### Get All Flight Seat by Flight id

```http
GET api/v1/flightSeats/flight/{id}
```

| Parameter | Type     | Description                    |
| :-------- | :------- | :----------------------------- |
| `id`      | `string` | **Required**. Flight ID        |
| `limit`   | `int`    | Number of results per page     |





### Get User Profile

```http
GET api/v1/auth/me
```

| Query | Type     | Description                    |
| :-------- | :------- | :----------------------------- |
| `token`                 | `string` | **Required**. Verification token          |


### Update User Profile

```http
PATCH api/v1/auth/me
```

| Query | Type     | Description                                                        |
| :-------- | :------- |:-------------------------------------------------------------------|
| `token`                 | `string` | **Required**. Verification token                                   |
| `updateProfileRequest` | `object` | **Required**. Update profile request payload (FullName/phoneNumber |


### Create Transaction

```http
  POST api/v1/transactions/payment
```

| Query              | Type     | Description                    |
| :----------------- | :------- | :----------------------------- |
| `token`                 | `string` | **Required**. Verification token          |
| `flightId`         | `string` | **Required**. Flight ID        |
| `adult`            | `int`    | **Required**. Number of adults |
| `child`            | `int`    | **Required**. Number of children |
| `baby`             | `int`    | **Required**. Number of babies |
| `transactionRequest` | `object` | **Required**. Transaction request payload |

### Get All Notification
```http
GET api/v1/notifications
```
| Query  | Type     | Description                    |
| :----- | :------- | :----------------------------- |
| `token`                 | `string` | **Required**. Verification token          |
| `limit` | `int`   | Number of results per page     |


### Get Transaction by Id
```http
  GET api/v1/transactions/{id}
```
| Parameter | Type     | Description                    |
| :-------- | :------- | :----------------------------- |
| `token`                 | `string` | **Required**. Verification token          |
| `id`      | `string` | **Required**. Transaction ID   |


### Get All Transaction History, filter and search

```http
  GET api/v1/transactions
```
| Query         | Type     | Description                    |
| :------------ | :------- | :----------------------------- |
| `token`                 | `string` | **Required**. Verification token          |
| `limit`       | `int`    | Number of results per page     |
| `startDate`   | `string` | Start date for filtering (YYYY-MM-DD) |
| `endDate`     | `string` | End date for filtering (YYYY-MM-DD)   |
| `flightCode`  | `string` | Flight code for filtering      |








