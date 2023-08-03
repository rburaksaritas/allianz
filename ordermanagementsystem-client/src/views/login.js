import React from 'react'
import { Link } from 'react-router-dom'

import { Helmet } from 'react-helmet'

import './login.css'

const Login = (props) => {
  return (
    <div className="login-container">
      <Helmet>
        <title>Login - Medica template</title>
        <meta property="og:title" content="Login - Medica template" />
      </Helmet>
      <header data-thq="thq-navbar" className="login-navbar">
        <div className="login-left">
          <Link to="/" className="login-navlink">
            <img
              alt="allianzlogo"
              src="/external/pastedimage-75g2.svg"
              className="login-pasted-image"
            />
          </Link>
        </div>
        <div data-thq="thq-navbar-btn-group" className="login-right">
          <Link to="/" className="login-book button button-main">
            <span className="login-text">
              <span>Register</span>
              <br></br>
            </span>
          </Link>
        </div>
        <div data-thq="thq-burger-menu" className="login-burger-menu">
          <svg viewBox="0 0 1024 1024" className="login-icon">
            <path d="M128 554.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667zM128 298.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667zM128 810.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667z"></path>
          </svg>
        </div>
        <div data-thq="thq-mobile-menu" className="login-mobile-menu">
          <div
            data-thq="thq-mobile-menu-nav"
            data-role="Nav"
            className="login-nav"
          >
            <div className="login-container1">
              <img
                alt="image"
                src="/Branding/logo-1500h.png"
                className="login-image"
              />
              <div data-thq="thq-close-menu" className="login-menu-close">
                <svg viewBox="0 0 1024 1024" className="login-icon2">
                  <path d="M810 274l-238 238 238 238-60 60-238-238-238 238-60-60 238-238-238-238 60-60 238 238 238-238z"></path>
                </svg>
              </div>
            </div>
            <nav
              data-thq="thq-mobile-menu-nav-links"
              data-role="Nav"
              className="login-nav1"
            >
              <span className="login-text03">Features</span>
              <span className="login-text04">How it works</span>
              <span className="login-text05">Prices</span>
              <span className="login-text06">Contact</span>
              <a href="#book" className="login-book1 button button-main">
                <img
                  alt="image"
                  src="/Icons/calendar.svg"
                  className="login-image1"
                />
                <span className="login-text07">Book an appointment</span>
              </a>
            </nav>
          </div>
        </div>
      </header>
      <section id="register" className="login-register">
        <div className="login-heading">
          <h2 className="login-text08">Login</h2>
          <p className="login-text09">
            Login as Customer, Staff, Manager or Admin.
          </p>
        </div>
        <div className="login-form">
          <div className="login-inputs">
            <input
              type="email"
              required
              placeholder="Email or Username"
              autoComplete="email"
              className="input book-input"
            />
            <input
              type="password"
              required
              placeholder="Password"
              autoComplete="new-password"
              className="input book-input"
            />
            <div className="login-lower">
              <div className="login-button">
                <button className="login-book2 button button-main">
                  <span>
                    <span>Login</span>
                    <br></br>
                  </span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>
      <div className="login-footer">
        <div className="login-left1">
          <div className="login-brand">
            <img
              alt="image"
              src="/aztrlogobeyazasset%202.svg"
              className="login-image2"
            />
          </div>
        </div>
        <div className="login-right1">
          <div className="login-legal">
            <a
              href="https://github.com/rburaksaritas"
              target="_blank"
              rel="noreferrer noopener"
              className="login-copyright"
            >
              <span>github.com/rburaksaritas </span>
              <span>© 2023  </span>
              <span>All Rights Reserved.</span>
            </a>
          </div>
          <div className="login-socials">
            <a
              href="https://linkedin.com/in/rburaksaritas"
              target="_blank"
              rel="noreferrer noopener"
              className="login-link"
            >
              <div className="login-social social">
                <img
                  alt="image"
                  src="/Icons/insider.svg"
                  className="login-image3"
                />
              </div>
            </a>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Login
