import React, { useState, useEffect } from "react";

const Countdown = () => {
  const [count, setCount] = useState(10);

  useEffect(() => {
    console.log("Countdown Mounted - timer started");

    const timer = setInterval(() => {
      setCount((prev) => {
        if (prev <= 1) {
          clearInterval(timer);
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => {
      console.log("Countdown Unmounted - timer cleared");
      clearInterval(timer);
    };
  }, []);

  return <h2>Countdown: {count}</h2>;
};

export default Countdown;