import React from 'react';
import {ContactAdminForm} from "../../components/contact-admin-form/ContactAdminForm.tsx";
import styles from './style.module.scss';

const ContactAdminPage: React.FC = () => {
    return (
        <div  className={styles['contact-admin-container']}>
            <div className={styles['contact-admin']}>
                <h2>Contact Admin</h2>
                <ContactAdminForm/>
            </div>
        </div>
    );
};

export default ContactAdminPage;
