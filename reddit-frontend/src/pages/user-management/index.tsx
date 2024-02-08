import styles from "./style.module.scss";
import React, { useState, useEffect } from "react";
import { Table, Button, message, Spin } from "antd";
import { render } from "react-dom";
// import { User } from "../types/User";

const UserManagement: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);

  const [isLoading, setIsLoading] = useState<boolean>(false);

  const jwtToken: string =
    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XX0.J2_B1Y8STCtF_8oQF0gndAklds6dezvR6SJocK-sB9g";

  useEffect(() => {
    setIsLoading(true);
    fetch("http://localhost:8083/user-service/admin/user", {
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
        message.success("User loaded!");
        setUsers(data);
      })
      .catch((error) => {
        message.error(error.message);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  const changeUserStatus = (id, newActive) => {
    const endpoint = `http://localhost:8083/user-service/admin/user/${id}/${
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
        // Update the local state to reflect the change
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
        const year = date.getFullYear();
        const month = date.toLocaleString("default", { month: "long" });
        // const month = date.getMonth();
        const day = date.getDate();

        const hour = date.getHours();
        const minute = date.getMinutes().toString().padStart(2, "0");
        const second = date.getSeconds().toString().padStart(2, "0");

        // Adjust for your local timezone if necessary
        const isAM = hour < 12;
        const adjustedHour = hour % 12 || 12;

        const customReadableFormat = `${month} ${day}, ${year} ${adjustedHour}:${minute}:${second} ${
          isAM ? "AM" : "PM"
        }`;

        return <span>{customReadableFormat}</span>;
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
        <>
          {record.type === "user" && (
            <Button
              type={"primary"}
              danger={record.active === true}
              onClick={() =>
                changeUserStatus(record.id, record.active ? false : true)
              }
            >
              {record.active ? "Ban" : "Activate"}
            </Button>
          )}
        </>
      ),
    },
  ];

  return (
    <div className={styles.table_wrapper}>
      {isLoading ? (
        <Spin tip="Loading..." />
      ) : (
        <Table
          className={styles.table}
          dataSource={users}
          columns={columns}
          rowKey="userId"
        />
      )}
    </div>
  );
};

export default UserManagement;

export interface User {
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
  success: true;
}
