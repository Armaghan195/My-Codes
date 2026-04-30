const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();
const { MongoMemoryServer } = require('mongodb-memory-server');
const Feedback = require('./models/Feedback');

const app = express();

// Middleware
app.use(cors());
app.use(express.json()); // Allows us to parse JSON bodies

// Connect to MongoDB (Using In-Memory Database for easy setup)
const connectDB = async () => {
  try {
    const mongoServer = await MongoMemoryServer.create();
    const mongoURI = mongoServer.getUri();
    await mongoose.connect(mongoURI);
    console.log(`MongoDB connected successfully to In-Memory DB: ${mongoURI}`);
  } catch (err) {
    console.error('MongoDB connection error:', err);
  }
};
connectDB();

// --- MOCK AUTHENTICATION MIDDLEWARE ---
// A simple middleware to check if the user is an admin.
// For example, they can pass ?admin=true in the URL or header.
const requireAdmin = (req, res, next) => {
  const isAdmin = req.query.admin === 'true' || req.headers['x-admin'] === 'true';
  if (isAdmin) {
    next(); // allow access
  } else {
    res.status(403).json({ message: 'Access denied. Admin only.' });
  }
};

// --- ROUTES ---

// 1. Submit feedback (POST /api/feedback)
app.post('/api/feedback', async (req, res) => {
  try {
    const { name, subject, rating, comments } = req.body;
    
    // Create new feedback entry
    const newFeedback = new Feedback({
      name,
      subject,
      rating,
      comments
    });

    // Save to database
    const savedFeedback = await newFeedback.save();
    res.status(201).json(savedFeedback);
  } catch (error) {
    console.error('Error saving feedback:', error);
    res.status(400).json({ message: 'Failed to submit feedback', error: error.message });
  }
});

// 2. Retrieve all feedbacks (GET /api/feedbacks) - Admin only
app.get('/api/feedbacks', requireAdmin, async (req, res) => {
  try {
    // Basic pagination implementation (bonus requirement)
    const page = parseInt(req.query.page) || 1;
    const limit = parseInt(req.query.limit) || 10;
    const skipIndex = (page - 1) * limit;

    const feedbacks = await Feedback.find()
      .sort({ createdAt: -1 }) // Sort by newest first
      .skip(skipIndex)
      .limit(limit);
      
    const total = await Feedback.countDocuments();
    
    res.status(200).json({
      feedbacks,
      currentPage: page,
      totalPages: Math.ceil(total / limit),
      totalCount: total
    });
  } catch (error) {
    console.error('Error fetching feedbacks:', error);
    res.status(500).json({ message: 'Failed to retrieve feedbacks' });
  }
});

// 3. Retrieve feedback by subject (GET /api/feedbacks/:subject) - Admin only
app.get('/api/feedbacks/subject/:subject', requireAdmin, async (req, res) => {
  try {
    const subject = req.params.subject;
    const feedbacks = await Feedback.find({ subject: new RegExp(`^${subject}$`, 'i') }); // Case-insensitive matching
    
    res.status(200).json(feedbacks);
  } catch (error) {
    console.error('Error fetching feedback by subject:', error);
    res.status(500).json({ message: 'Failed to retrieve feedbacks by subject' });
  }
});

// Start the server
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
