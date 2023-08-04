import React, { useState, useEffect } from 'react';
import { Helmet } from 'react-helmet'
import { fetchDataFromApi, renderCustomersTable } from './helpers';

import './admin.css'

const Admin = (props) => {

  const [customersData, setCustomersData] = useState([]);
  useEffect(() => {
    // Fetch customer data from the backend API when the component mounts
    fetchDataFromApi('customer/get', setCustomersData);
  }, []);

  return (
    <div className="admin-container">
      <Helmet>
        <title>Admin - Allianz OMS</title>
        <meta property="og:title" content="Admin - Medica template" />
      </Helmet>
      <header data-role="Header" className="admin-header">
        <div className="admin-container1">
          <img
            alt="allianzlogo"
            src="/external/pastedimage-75g2.svg"
            className="admin-pasted-image"
          />
          <div className="admin-container2">
            <div className="admin-nav">
              <nav className="admin-nav1">
                <span className="admin-text">Customers</span>
                <span className="admin-text01">Staff</span>
                <span className="admin-text02">Orders</span>
                <span className="admin-text03">Managers</span>
                <span className="admin-text04">Products</span>
              </nav>
            </div>
          </div>
        </div>
        <div className="admin-container3">
          <div className="admin-container4">
            <div className="admin-btn-group"></div>
          </div>
          <a href="#register" className="admin-book button button-main">
            <span className="admin-text05">
              <span>Logout</span>
              <br></br>
            </span>
          </a>
        </div>
      </header>
      {renderCustomersTable(customersData)}
      <div className="admin-footer">
        <div className="admin-left">
          <div className="admin-brand">
            <img
              alt="image"
              src="/aztrlogobeyazasset%202.svg"
              className="admin-image1"
            />
          </div>
        </div>
        <div className="admin-right">
          <div className="admin-legal">
            <a
              href="https://github.com/rburaksaritas"
              target="_blank"
              rel="noreferrer noopener"
              className="admin-copyright"
            >
              <span>github.com/rburaksaritas </span>
              <span>© 2023 </span>
              <span>All Rights Reserved.</span>
            </a>
          </div>
          <div className="admin-socials">
            <a
              href="https://linkedin.com/in/rburaksaritas"
              target="_blank"
              rel="noreferrer noopener"
              className="admin-link"
            >
              <div className="admin-social social">
                <img
                  alt="image"
                  src="/Icons/insider.svg"
                  className="admin-image2"
                />
              </div>
            </a>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Admin
