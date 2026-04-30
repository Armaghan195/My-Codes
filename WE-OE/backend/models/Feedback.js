const mongoose = require('mongoose');

// Define the schema for feedback
const feedbackSchema = new mongoose.Schema({
  name: {
    type: String,
    required: [true, 'Student name is required'],
  },
  subject: {
    type: String,
    required: [true, 'Subject is required'],
  },
  rating: {
    type: Number,
    required: [true, 'Rating is required'],
    min: [1, 'Rating must be at least 1'],
    max: [5, 'Rating cannot be more than 5'],
  },
  comments: {
    type: String,
    // Comments are optional
  },
}, {
  timestamps: true // This adds createdAt and updatedAt fields automatically
});

const Feedback = mongoose.model('Feedback', feedbackSchema);

module.exports = Feedback;
