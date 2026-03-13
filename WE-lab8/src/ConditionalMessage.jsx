import React, { useState } from "react";

const ChildMessage = ({ message }) => {
  return <h3>{message}</h3>;
};

const ConditionalMessage = () => {
  const [show, setShow] = useState(false);

  return (
    <div>
      <button onClick={() => setShow(!show)}>
        {show ? "Hide" : "Show"} Prop Message
      </button>
      {show && <ChildMessage message="Hello from props!" />}
    </div>
  );
};

export default ConditionalMessage;