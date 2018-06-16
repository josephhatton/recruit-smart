(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('ApplicantCommentSearch', ApplicantCommentSearch);

  ApplicantCommentSearch.$inject = ['$resource'];

  function ApplicantCommentSearch($resource) {
    var resourceUrl = 'api/_search/applicant-comments/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
