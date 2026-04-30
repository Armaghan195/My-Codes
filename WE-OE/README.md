# Student Feedback Application (WE-OE Lab)

This is a beginner-friendly full-stack web application built for the WE-OE Lab. It allows students to submit feedback for various subjects and provides an admin interface to manage and view the data.

## Features Built
- **React Frontend**: Built with Vite, using `react-router-dom` for navigation.
- **Express Backend**: A simple REST API handling data processing.
- **MongoDB Database**: Uses an In-Memory MongoDB instance for quick local development without needing a local database setup.
- **Formik & Yup**: Real-time form validation for student feedback submissions.
- **Admin Dashboard**: Contains mocked authentication, subject filtering, dynamic average rating calculation, and simple pagination ("Load More").

## How to Run Locally

You will need two separate terminal windows to run both the frontend and backend simultaneously.

### 1. Start the Backend
```bash
cd backend
npm install
node server.js
```

### 2. Start the Frontend
```bash
cd frontend
npm install
npm run dev
```

The React app will usually start at `http://localhost:5173`.
