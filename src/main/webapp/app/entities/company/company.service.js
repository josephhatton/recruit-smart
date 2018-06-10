(function() {
    'use strict';
    angular
        .module('recruitsmartApp')
        .factory('Company', Company);

    Company.$inject = ['$resource'];

    function Company ($resource) {
        var resourceUrl =  'api/companies/:id';
        var resourceJobUrl =  'api/companies/job-order/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        }),
        $resource(resourceJobUrl, {}, {
            'jobs': { method: 'GET', isArray: true}
        });
    }
})();
