import React, { useState } from "react";
import Login from "./Login";
import Message from "./Message";
import ToggleMessage from "./ToggleMessage";
import ConditionalMessage from "./ConditionalMessage";
import Countdown from "./Countdown";

function App() {
  const [showMessage, setShowMessage] = useState(true);
  const [showCountdown, setShowCountdown] = useState(false);

  return (
    <div style={{ padding: "20px", fontFamily: "Arial" }}>
      <h1>Lab 7 - React Conditional Rendering & Lifecycle</h1>

      <hr />
      <h2>Part A: Login Toggle</h2>
      <Login />

      <hr />
      <h2>Part B: Message Lifecycle</h2>
      <button onClick={() => setShowMessage(!showMessage)}>
        {showMessage ? "Hide Message" : "Show Message"}
      </button>
      {showMessage && <Message />}

      <hr />
      <h2>Part D - Exercise 1 & 2: Toggle Multiple Messages</h2>
      <ToggleMessage />

      <hr />
      <h2>Part D - Exercise 3: Conditional Rendering with Props</h2>
      <ConditionalMessage />

      <hr />
      <h2>Part D - Exercise 4: Countdown Timer</h2>
      <button onClick={() => setShowCountdown(!showCountdown)}>
        {showCountdown ? "Hide" : "Start"} Countdown
      </button>
      {showCountdown && <Countdown />}
    </div>
  );
}

export default App;