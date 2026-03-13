import React, { useState, useEffect } from "react";

const SingleMessage = ({ text }) => {
  useEffect(() => {
    console.log(`"${text}" Mounted`);
    return () => console.log(`"${text}" Unmounted`);
  }, [text]);

  return <p>{text}</p>;
};

const ToggleMessage = () => {
  const [showMsg1, setShowMsg1] = useState(false);
  const [showMsg2, setShowMsg2] = useState(false);

  return (
    <div>
      <button onClick={() => setShowMsg1(!showMsg1)}>Toggle Message 1</button>
      <button onClick={() => setShowMsg2(!showMsg2)}>Toggle Message 2</button>
      {showMsg1 && <SingleMessage text="This is Message 1" />}
      {showMsg2 && <SingleMessage text="This is Message 2" />}
    </div>
  );
};

export default ToggleMessage;