import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';

// Beginner-friendly validation schema using Yup
const validationSchema = Yup.object({
  name: Yup.string()
    .required('Name is required')
    .min(2, 'Name must be at least 2 characters'),
  subject: Yup.string()
    .required('Please select a subject'),
  rating: Yup.number()
    .required('Rating is required')
    .min(1, 'Rating must be between 1 and 5')
    .max(5, 'Rating must be between 1 and 5'),
  comments: Yup.string()
});

const FeedbackForm = () => {
  const [submitStatus, setSubmitStatus] = useState(null); // 'success' or 'error'

  const formik = useFormik({
    initialValues: {
      name: '',
      subject: '',
      rating: '',
      comments: ''
    },
    validationSchema: validationSchema,
    onSubmit: async (values, { resetForm }) => {
      try {
        // Send POST request to backend
        const response = await axios.post('http://localhost:5000/api/feedback', values);
        
        if (response.status === 201) {
          setSubmitStatus('success');
          resetForm(); // clear form
          
          // Hide success message after 3 seconds
          setTimeout(() => setSubmitStatus(null), 3000);
        }
      } catch (error) {
        console.error('Error submitting form:', error);
        setSubmitStatus('error');
      }
    },
  });

  return (
    <div className="card">
      <h2>Submit Your Feedback</h2>
      <p>We value your opinion on our subjects.</p>
      
      {/* Success/Error Messages */}
      {submitStatus === 'success' && (
        <div className="alert success">Feedback submitted successfully! Thank you.</div>
      )}
      {submitStatus === 'error' && (
        <div className="alert error">Failed to submit feedback. Please try again.</div>
      )}

      <form onSubmit={formik.handleSubmit} className="feedback-form">
        
        {/* Name Field */}
        <div className="form-group">
          <label>Student Name:</label>
          <input
            type="text"
            name="name"
            value={formik.values.name}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            placeholder="Enter your full name"
          />
          {formik.touched.name && formik.errors.name ? (
            <div className="error-text">{formik.errors.name}</div>
          ) : null}
        </div>

        {/* Subject Field */}
        <div className="form-group">
          <label>Subject:</label>
          <select
            name="subject"
            value={formik.values.subject}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
          >
            <option value="">-- Select a Subject --</option>
            <option value="Math">Math</option>
            <option value="Science">Science</option>
            <option value="History">History</option>
            <option value="English">English</option>
            <option value="Computer Science">Computer Science</option>
          </select>
          {formik.touched.subject && formik.errors.subject ? (
            <div className="error-text">{formik.errors.subject}</div>
          ) : null}
        </div>

        {/* Rating Field */}
        <div className="form-group">
          <label>Rating (1-5):</label>
          <input
            type="number"
            name="rating"
            min="1"
            max="5"
            value={formik.values.rating}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            placeholder="5"
          />
          {formik.touched.rating && formik.errors.rating ? (
            <div className="error-text">{formik.errors.rating}</div>
          ) : null}
        </div>

        {/* Comments Field */}
        <div className="form-group">
          <label>Additional Comments (Optional):</label>
          <textarea
            name="comments"
            rows="4"
            value={formik.values.comments}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            placeholder="Any suggestions or comments..."
          />
        </div>

        <button type="submit" className="btn btn-primary" disabled={formik.isSubmitting}>
          {formik.isSubmitting ? 'Submitting...' : 'Submit Feedback'}
        </button>
      </form>
    </div>
  );
};

export default FeedbackForm;
