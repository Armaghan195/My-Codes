# Node.js Authentication Backend

This is a simple Node.js and Express backend with MongoDB integration for user login and registration tasks. It uses JWT for authentication and bcrypt for password hashing.

## Prerequisites
- Node.js installed on your machine.
- A MongoDB Atlas account and connection string.

## Setup Instructions

**1. Navigate to the project folder:**
Open your terminal and navigate to the backend folder:
```bash
cd backend
```

**2. Install dependencies:**
Install the required node modules by running:
```bash
npm install
```

**3. Configure Environment Variables:**
You need exactly one file named `.env` in the root of the `backend` folder. Create it and add the following lines (replace `<your_mongodb_connection_string>` with your actual URL):
```txt
MONGO_URI=<your_mongodb_connection_string>
PORT=5000
JWT_SECRET=G7s9vQ2mLz8wX4pR6fT1bN0kY3uH5aD
```

**4. Start the server:**
Run the following command to start the backend with `nodemon` (auto-restarts on code changes):
```bash
npm run dev
```

You should see the following logs if successful:
```
Server running
MongoDB connected
```

## API Endpoints

Once the server is running on `http://localhost:5000`, you can use Postman to test the endpoints:

### Signup
- **URL**: `POST /api/auth/signup`
- **Body** (Raw -> JSON):
```json
{
  "username": "sampleuser",
  "email": "sample@example.com",
  "password": "123456"
}
```

### Login
- **URL**: `POST /api/auth/login`
- **Body** (Raw -> JSON):
```json
{
  "email": "sample@example.com",
  "password": "123456"
}
```
*(Successful login will return a generated JWT token)*
