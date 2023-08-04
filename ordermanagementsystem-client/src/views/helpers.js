
import React from 'react';

export const fetchDataFromApi = (urlSuffix, setterFunction) => {
  React.useEffect(() => {
    // Fetch data from the backend API when the component mounts
    fetch(`http://localhost:8080/${urlSuffix}`)
      .then((response) => response.json())
      .then((data) => setterFunction(data))
      .catch((error) => console.error('Error fetching data:', error));
  }, []); // Empty dependency array to ensure it runs only once when the component mounts
};

export const renderCustomersTable = (customersData) => {
    return (
      <section id="content" className="admin-content">
        <h1 className="admin-text08">
          <span>Welcome</span>
          <br />
        </h1>
        <span>Below, you can see the list of customers.</span>
        <div className="admin-table-wrapper">
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Location</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Birth Date</th>
                {/* Add more table headers as needed */}
              </tr>
            </thead>
            <tbody>
              {customersData.map((customer) => (
                <tr key={customer.id}>
                  <td>{customer.name}</td>
                  <td>{customer.location}</td>
                  <td>{customer.phone}</td>
                  <td>{customer.mail}</td>
                  <td>{customer.birthDate}</td>
                  {/* Add more table data (td) cells for other properties */}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    );
  };
  

