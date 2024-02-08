import React, { useState, useEffect } from "react";
import { Table, Button, message, Spin } from "antd";
import styles from "./style.module.scss"; // Ensure the path matches your file structure

interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  dateJoined: string; // ISO date string
  type: "user" | "admin" | "superadmin";
  active: boolean;
}

interface ActionResponseData {
  message: string;
  success: boolean; // Ensure this matches the possible values (true for success cases)
}

const UserManagement: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const jwtToken: string =
    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XX0.J2_B1Y8STCtF_8oQF0gndAklds6dezvR6SJocK-sB9g";

  useEffect(() => {
    // Replace the existing useEffect code with this
    fetch("http://localhost:8081/user-service/admin/user", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        message.success(data.message);
        setUsers(data.userList); // Set the state with the userList
      })
      .catch((error) => {
        message.error(error.message);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  const changeUserStatus = (id: string, newActive: boolean) => {
    const endpoint = `http://localhost:8081/user-service/admin/user/${id}/${
      newActive ? "activate" : "deactivate"
    }`;
    fetch(endpoint, {
      method: "PATCH",
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    })
      .then((response) => response.json())
      .then((data: ActionResponseData) => {
        if (!data.success) {
          throw new Error(data.message);
        }
        message.success(data.message);
        setUsers(
          users.map((user) =>
            user.id === id ? { ...user, active: newActive } : user
          )
        );
      })
      .catch((error) => {
        message.error(error.message);
      });
  };

  const columns = [
    { title: "UserID", dataIndex: "id", key: "userId" },
    {
      title: "Full Name",
      render: (_, record: User) => `${record.firstName} ${record.lastName}`,
      key: "fullName",
    },
    { title: "Email", dataIndex: "email", key: "email" },
    {
      title: "Date Joined",
      dataIndex: "dateJoined",
      key: "dateJoined",
      render: (dateJoined: string) => {
        const date = new Date(dateJoined);
        return date.toLocaleString();
      },
    },
    { title: "Type", dataIndex: "type", key: "type" },
    {
      title: "Status",
      dataIndex: "active",
      key: "status",
      render: (active: boolean) => (active ? "Active" : "Banned"),
    },
    {
      title: "Action",
      key: "action",
      render: (_, record: User) => (
        <Button
          type="primary"
          danger={record.active}
          onClick={() => changeUserStatus(record.id, !record.active)}
        >
          {record.active ? "Ban" : "Activate"}
        </Button>
      ),
    },
  ];

  return (
    <div className={styles.table_wrapper}>
      {isLoading ? (
        <Spin tip="Loading..." />
      ) : (
        <Table dataSource={users} columns={columns} rowKey="id" />
      )}
    </div>
  );
};

export default UserManagement;
