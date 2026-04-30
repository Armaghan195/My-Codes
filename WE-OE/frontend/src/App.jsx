import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import FeedbackForm from './components/FeedbackForm';
import FeedbackList from './components/FeedbackList';
import './App.css'; // We will use this for some basic layout

function App() {
  return (
    <Router>
      <div className="app-container">
        {/* Simple Navigation Bar */}
        <nav className="navbar">
          <h2>School Feedback System</h2>
          <div className="nav-links">
            <Link to="/">Submit Feedback (Student)</Link>
            <Link to="/admin">Admin Dashboard</Link>
          </div>
        </nav>

        {/* Main Content Area */}
        <main className="main-content">
          <Routes>
            <Route path="/" element={<FeedbackForm />} />
            <Route path="/admin" element={<FeedbackList />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
