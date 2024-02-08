import React, { useEffect, useState } from "react";
import { Button, Result, Spin } from "antd";
import { useLocation, useNavigate } from "react-router-dom"; // or another way to access the query string

const VerificationPage: React.FC = () => {
  const navigate = useNavigate();
  const [verificationState, setVerificationState] = useState<
    "loading" | "success" | "error"
  >("loading");
  const location = useLocation(); // This hook gives you the current location object
  const [message, setMessage] = useState("");

  useEffect(() => {
    // Function to parse query params
    const parseQuery = (queryString: string) => {
      const query: any = {};
      const pairs = (
        queryString[0] === "?" ? queryString.substr(1) : queryString
      ).split("&");
      for (let i = 0; i < pairs.length; i++) {
        const pair = pairs[i].split("=");
        query[decodeURIComponent(pair[0])] = decodeURIComponent(pair[1] || "");
      }
      return query;
    };

    const query = parseQuery(location.search);
    const token = query.token;

    if (token) {
      // Send the verification token to the server
      fetch(`http://localhost:8081/user-service/user/verify`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ token }), // Send the token in the body, adjust if your API expects something different
      })
        .then((response) => response.json())
        .then((data) => {
          if (data.success) {
            console.log(data);
            setVerificationState("success");
            setMessage(data.message);
          } else {
            console.log(data);
            setVerificationState("error");
            setMessage(data.message);
          }
        })
        .catch((error) => {
          console.log(error);
          setVerificationState("error");
          setMessage(error.message);
        });
    } else {
      setVerificationState("error");
    }
  }, [location]);

  if (verificationState === "loading") {
    return <Spin size="large" />;
  }

  let resultComponent;

  if (verificationState === "success") {
    resultComponent = (
      <Result
        status="success"
        title="Verification Successful!"
        subTitle={message}
        extra={[
          <Button
            type="primary"
            key="console"
            onClick={() => navigate("/home")}
          >
            Go to HomePage
          </Button>,
        ]}
      />
    );
  } else if (verificationState === "error") {
    resultComponent = (
      <Result
        status="error"
        title="Verification Failed"
        subTitle={message}
        extra={[
          <Button
            type="primary"
            key="console"
            onClick={() => navigate("/home")}
          >
            Go to HomePage
          </Button>,
        ]}
      />
    );
  }

  return resultComponent;
};

export default VerificationPage;
