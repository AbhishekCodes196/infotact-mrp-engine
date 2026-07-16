 const BASE_URL = 'http://localhost:8083';

 document.addEventListener('DOMContentLoaded', () => {
    loadParts();
    loadActiveBomLinks();
});


function loadParts() {
    fetch(`${BASE_URL}/parts`) 
        .then(response => response.json())
        .then(parts => {
            displayPartsTable(parts);
            populateDropdowns(parts);
        })
        .catch(error => console.error('Error loading parts:', error));
}

function displayPartsTable(parts) {
    const tbody = document.getElementById('partTableBody');
    tbody.innerHTML = '';

    if (parts.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" style="text-align: center; color: #64748b;">No parts found in the system.</td></tr>`;
        return;
    }

    parts.forEach(part => {		
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${part.id}</td>
            <td><strong>${part.partName}</strong></td>
            <td>${part.currentStock}</td>
            <td>${part.leadTimeDays} Days</td>
            <td>
                <button onclick="editPart(${part.id}, '${part.partName}', ${part.currentStock}, ${part.leadTimeDays})" style="padding: 4px 10px !important; font-size: 0.85rem; margin-right: 5px;">Edit</button>
                <button onclick="deletePart(${part.id})" style="padding: 4px 10px !important; font-size: 0.85rem; background-color: #dc2626; color: white; border: none; border-radius: 4px; cursor: pointer;">Delete</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function populateDropdowns(parts) {
    const parentSelect = document.getElementById('parentPart');
    const childSelect = document.getElementById('childPart');
    const mrpSelect = document.getElementById('mrpTargetPart');

     parentSelect.innerHTML = '<option value="">Select Parent...</option>';
    childSelect.innerHTML = '<option value="">Select Component...</option>';
    mrpSelect.innerHTML = '<option value="">Select Target Product...</option>';

    parts.forEach(part => {
        const optionText = `${part.partName} (ID: ${part.id})`;
        
        parentSelect.innerHTML += `<option value="${part.id}">${optionText}</option>`;
        childSelect.innerHTML += `<option value="${part.id}">${optionText}</option>`;
        mrpSelect.innerHTML += `<option value="${part.id}">${optionText}</option>`;
    });
}

 
function savePart() {
    const id = document.getElementById('partId').value;
    const partName = document.getElementById('partName').value;
    const currentStock = parseInt(document.getElementById('stock').value);
    const leadTimeDays = parseInt(document.getElementById('leadTime').value);

    if (!partName || isNaN(currentStock) || isNaN(leadTimeDays)) {
        alert('Please complete all fields before saving.');
        return;
    }

    const partPayload = { partName, currentStock, leadTimeDays };
    if (id) partPayload.id = parseInt(id);

     const url = id ? `${BASE_URL}/parts/${id}` : `${BASE_URL}/parts`;
    const method = id ? 'PUT' : 'POST';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(partPayload)
    })
    .then(response => response.json())
    .then(() => {
        clearPartForm();
        loadParts();
    })
    .catch(error => console.error('Error saving part:', error));
}

 
function deletePart(id) {
    if (confirm('Are you sure you want to permanently delete this part?')) {
        fetch(`${BASE_URL}/parts/${id}`, {
            method: 'DELETE'
        })
        .then(() => {
            loadParts();  
            loadActiveBomLinks();  
        })
        .catch(error => console.error('Error deleting part:', error));
    }
}

function editPart(id, name, stock, leadTime) {
    document.getElementById('partId').value = id;
    document.getElementById('partName').value = name;
    document.getElementById('stock').value = stock;
    document.getElementById('leadTime').value = leadTime;
}

function clearPartForm() {
    document.getElementById('partId').value = '';
    document.getElementById('partName').value = '';
    document.getElementById('stock').value = '';
    document.getElementById('leadTime').value = '';
}

 
function saveBomLink() {
    const parentItemId = document.getElementById('parentPart').value;
    const childItemId = document.getElementById('childPart').value;
    const quantityRequired = parseInt(document.getElementById('bomQuantity').value);

    if (!parentItemId || !childItemId || isNaN(quantityRequired)) {
        alert('Please select both parts and declare a valid quantity.');
        return;
    }

    if (parentItemId === childItemId) {
        alert('A part cannot be a component of itself.');
        return;
    }

    const bomPayload = {
        parentItemId: parseInt(parentItemId),
        childItemId: parseInt(childItemId),
        quantityRequired: quantityRequired
    };

    fetch(`${BASE_URL}/bom/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(bomPayload)
    })
    .then(response => response.json())
    .then(() => {
        alert('BOM link connected successfully!');
        document.getElementById('bomQuantity').value = '';
        loadActiveBomLinks(); 
    })
    .catch(error => console.error('Error establishing BOM link:', error));
}

 
function calculateMRP() {
    const partId = document.getElementById('mrpTargetPart').value;
    const quantity = parseInt(document.getElementById('mrpQuantity').value);

    if (!partId || isNaN(quantity) || quantity <= 0) {
        alert('Please specify both a target product and a valid production quantity.');
        return;
    }

    fetch(`${BASE_URL}/mrp/calculate?partId=${partId}&quantity=${quantity}`)
        .then(response => response.json())
        .then(results => {
            const tbody = document.getElementById('mrpResultsBody');
            tbody.innerHTML = '';

            if (results.length === 0) {
                tbody.innerHTML = `<tr><td colspan="7" style="text-align: center; color: #64748b; font-style: italic;">No nested bill-of-materials dependencies identified for this item execution.</td></tr>`;
                return;
            }

            results.forEach(item => {
                const tr = document.createElement('tr');
                
                if (item.netRequirement > 0) {
                    tr.setAttribute('style', 'background-color: #fff3cd;');
                }

                tr.innerHTML = `
                    <td>${item.id}</td>
                    <td><strong>${item.partName}</strong></td>
                    <td>${item.grossRequirement}</td>
                    <td>${item.currentStock}</td>
                    <td>${item.netRequirement}</td>
                    <td>${item.leadTimeDays} Days</td>
                    <td>
                        <span>${item.netRequirement > 0 ? `⚠️ Order ${item.netRequirement} units` : '✅ Stock Adequate'}</span>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => console.error('Error compiling calculation matrix:', error));
}

 
function resetDatabase() {
    if (confirm('Are you sure you want to clear out setup configurations? All data records will reset.')) {
        fetch(`${BASE_URL}/parts/reset`, { method: 'POST' }) 
            .then(() => {
                loadParts();
                loadActiveBomLinks(); // FIXED: Instantly wipe visual relationships grid clean when DB is reset
                document.getElementById('mrpResultsBody').innerHTML = `
                    <tr><td colspan="7" style="text-align: center; color: #64748b; font-style: italic;">No calculation run yet. Select a product and quantity above.</td></tr>`;
            })
            .catch(error => console.error('Error executing infrastructure reset:', error));
    }
}

 
function loadActiveBomLinks() {
     fetch(`${BASE_URL}/bom-links`)
        .then(response => response.json())
        .then(links => {
            const tableBody = document.getElementById('bomLinksTableBody');
            tableBody.innerHTML = '';

            if (links.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="4" class="text-center text-muted" style="text-align: center; color: #64748b; font-style: italic; padding: 20px !important;">No active relationships mapped yet.</td></tr>';
                return;
            }

            links.forEach(link => {
                 const parentId = link.parentItemId || (link.parentItem ? link.parentItem.id : 'N/A');
                const childId = link.childItemId || (link.childItem ? link.childItem.id : 'N/A');
                const qty = link.quantityRequired || link.quantity || 0;

                const row = `<tr>
                    <td><strong>${link.id}</strong></td>
                    <td>Part ID: ${parentId}</td>
                    <td>Part ID: ${childId}</td>
                    <td><span style="background: #2563eb; color: white; padding: 3px 8px; border-radius: 4px; font-weight: 600; font-size: 0.85rem;">${qty}</span></td>
                </tr>`;
                tableBody.insertAdjacentHTML('beforeend', row);
            });
        })
        .catch(error => console.error('Error fetching BOM links:', error));
}
