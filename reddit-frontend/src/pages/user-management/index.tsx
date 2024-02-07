import styles from "./style.module.scss";
import React, { useState, useEffect } from "react";
import { Table, Button, message } from "antd";
// import { User } from "../types/User";

const UserManagement: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
    // Replace with your API endpoint to fetch users
    fetch("user-service/admin/user")
      .then((response) => {
        // Check if the response is ok (status code 200-299)
        if (!response.ok) {
          // Throw an error if not ok
          throw new Error("Network response was not ok");
        }
        // Parse the JSON response
        return response.json();
      })
      .then((data) => {
        console.log(data);
        // Update the users state with the data
        setUsers(data);
        // // Set loading to false as the data has been successfully fetched
        // setIsLoading(false);
      })
      .catch((error) => {
        // Catch any errors and update the error state
        console.error("There was a problem with your fetch operation:", error);
        // setError(error);
        // setIsLoading(false);
      });

    setUsers(dummyUsers);
  }, []);

  const changeUserStatus = (userId: string, newStatus: "active" | "banned") => {
    // Implement the API call to update user status here
    setUsers(
      users.map((user) =>
        user.userId === userId ? { ...user, status: newStatus } : user
      )
    );
  };

  // dummy users
  const dummyUsers: User[] = [
    {
      userId: "1",
      fullName: "John Doe",
      email: "john.doe@example.com",
      dateJoined: "2022-01-15T14:48:00.000Z",
      type: "admin",
      status: "active",
    },
    {
      userId: "2",
      fullName: "Jane Smith",
      email: "jane.smith@example.com",
      dateJoined: "2022-03-22T09:26:00.000Z",
      type: "user",
      status: "active",
    },
    {
      userId: "3",
      fullName: "Mike Brown",
      email: "mike.brown@example.com",
      dateJoined: "2022-07-05T11:00:00.000Z",
      type: "user",
      status: "banned",
    },
    {
      userId: "4",
      fullName: "Emily Johnson",
      email: "emily.johnson@example.com",
      dateJoined: "2023-02-17T16:34:00.000Z",
      type: "user",
      status: "active",
    },
    {
      userId: "5",
      fullName: "Chris Green",
      email: "chris.green@example.com",
      dateJoined: "2023-04-21T13:45:00.000Z",
      type: "admin",
      status: "active",
    },
  ];

  const columns = [
    { title: "UserID", dataIndex: "userId", key: "userId" },
    { title: "Full Name", dataIndex: "fullName", key: "fullName" },
    { title: "Email", dataIndex: "email", key: "email" },
    { title: "Date Joined", dataIndex: "dateJoined", key: "dateJoined" },
    { title: "Type", dataIndex: "type", key: "type" },
    {
      title: "Action",
      key: "action",
      render: (_, record: User) => (
        <>
          {record.type !== "admin" && (
            <Button
              type={"primary"}
              danger={record.status !== "banned"}
              onClick={() =>
                changeUserStatus(
                  record.userId,
                  record.status === "active" ? "banned" : "active"
                )
              }
            >
              {record.status === "active" ? "Ban" : "Activate"}
            </Button>
          )}
        </>
      ),
    },
  ];

  return (
    <div className={styles.table_wrapper}>
      {/* <h1>Users</h1> */}
      <Table dataSource={users} columns={columns} rowKey="userId" />
    </div>
  );
};

export default UserManagement;

export interface User {
  userId: string;
  fullName: string;
  email: string;
  dateJoined: string; // ISO date string
  type: "user" | "admin";
  status: "active" | "banned";
}
