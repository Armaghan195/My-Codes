import React, { useState, useEffect } from 'react';
import axios from 'axios';

const FeedbackList = () => {
  const [feedbacks, setFeedbacks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  
  // Filter state
  const [subjectFilter, setSubjectFilter] = useState('');
  
  // Pagination state (bonus)
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);

  // Mock Authentication (bonus)
  const [isAdmin, setIsAdmin] = useState(false);

  // Function to fetch data from backend
  const fetchFeedbacks = async (pageNum = 1, reset = false) => {
    if (!isAdmin) return;
    
    setLoading(true);
    try {
      let url = `http://localhost:5000/api/feedbacks?page=${pageNum}&limit=5`;
      
      // We pass the "admin=true" in the query to pass our mock authentication middleware
      url += '&admin=true';
      
      const response = await axios.get(url);
      
      if (reset) {
        setFeedbacks(response.data.feedbacks);
      } else {
        setFeedbacks(prev => [...prev, ...response.data.feedbacks]);
      }
      
      // Check if we have more pages
      if (pageNum >= response.data.totalPages) {
        setHasMore(false);
      } else {
        setHasMore(true);
      }
      
    } catch (err) {
      console.error('Error fetching feedbacks:', err);
      setError('Failed to load feedbacks. You might not have admin rights or the server is down.');
    } finally {
      setLoading(false);
    }
  };

  // Fetch feedback by subject (for filtering)
  const fetchFeedbacksBySubject = async (subject) => {
    if (!isAdmin) return;
    
    setLoading(true);
    try {
      // Mock auth included
      const response = await axios.get(`http://localhost:5000/api/feedbacks/subject/${subject}?admin=true`);
      setFeedbacks(response.data);
      setHasMore(false); // Disable pagination when filtering
    } catch (err) {
      setError('Failed to load filtered feedbacks.');
    } finally {
      setLoading(false);
    }
  };

  // Run when component mounts or when admin status changes
  useEffect(() => {
    if (isAdmin) {
      if (subjectFilter === '') {
        fetchFeedbacks(1, true); // Reset and fetch page 1
        setPage(1);
      } else {
        fetchFeedbacksBySubject(subjectFilter);
      }
    }
  }, [isAdmin, subjectFilter]);

  // Load more function
  const loadMore = () => {
    const nextPage = page + 1;
    setPage(nextPage);
    fetchFeedbacks(nextPage, false);
  };

  // Calculate average rating (bonus)
  const calculateAverageRating = () => {
    if (feedbacks.length === 0) return 0;
    const total = feedbacks.reduce((acc, curr) => acc + curr.rating, 0);
    return (total / feedbacks.length).toFixed(1);
  };

  // Mock Login Function
  const handleLogin = () => {
    setIsAdmin(true);
  };

  // If not logged in as admin, show login button
  if (!isAdmin) {
    return (
      <div className="card">
        <h2>Admin Dashboard Login</h2>
        <p>You need to be an admin to view this page.</p>
        <button onClick={handleLogin} className="btn btn-primary">
          Login as Admin (Mock)
        </button>
      </div>
    );
  }

  return (
    <div className="admin-container">
      <h2>Admin Dashboard: Feedback List</h2>
      
      {/* Filter Section */}
      <div className="filter-section card">
        <label>Filter by Subject: </label>
        <select 
          value={subjectFilter} 
          onChange={(e) => setSubjectFilter(e.target.value)}
        >
          <option value="">All Subjects</option>
          <option value="Math">Math</option>
          <option value="Science">Science</option>
          <option value="History">History</option>
          <option value="English">English</option>
          <option value="Computer Science">Computer Science</option>
        </select>
        
        {/* Average Rating Display */}
        <div className="average-rating">
          <strong>Average Rating:</strong> {calculateAverageRating()} / 5.0
          <span style={{ fontSize: '0.8em', color: '#666', marginLeft: '10px' }}>
            (Based on {feedbacks.length} reviews shown)
          </span>
        </div>
      </div>

      {error && <div className="alert error">{error}</div>}

      {/* Display Feedbacks */}
      <div className="feedback-list">
        {feedbacks.length === 0 && !loading ? (
          <p>No feedbacks found.</p>
        ) : (
          feedbacks.map((fb) => (
            <div key={fb._id} className="feedback-card card">
              <div className="feedback-header">
                <h3>{fb.subject}</h3>
                <span className="rating-badge">★ {fb.rating}/5</span>
              </div>
              <p><strong>Student:</strong> {fb.name}</p>
              {fb.comments && <p><strong>Comments:</strong> {fb.comments}</p>}
              <span className="date-text">
                {new Date(fb.createdAt).toLocaleDateString()}
              </span>
            </div>
          ))
        )}
      </div>

      {/* Pagination Load More Button */}
      {loading && <p>Loading...</p>}
      
      {hasMore && subjectFilter === '' && !loading && (
        <button onClick={loadMore} className="btn btn-secondary load-more">
          Load More Feedbacks
        </button>
      )}
    </div>
  );
};

export default FeedbackList;
