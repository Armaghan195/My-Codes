import React, { useEffect } from "react";

const Message = () => {
  useEffect(() => {
    console.log("Message Component Mounted"); // runs on mount

    return () => {
      console.log("Message Component Unmounted"); // runs on unmount
    };
  }, []);

  return <h2>Hello! I am a message.</h2>;
};

export default Message;
